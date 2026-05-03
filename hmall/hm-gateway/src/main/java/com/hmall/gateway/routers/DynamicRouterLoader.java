package com.hmall.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hmall.common.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @version 1.0
 * @ProjectName: hmall
 * @Package: com.hmall.gateway.routers
 * @Description:
 * @Author: Simon
 * @CreateDate: 2026/5/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouterLoader {

    private final NacosConfigManager nacosConfigManager;

    private final RouteDefinitionWriter writer;

    // 保存当前存储加载过的路由id
    private final Set<String> routeIds = new HashSet<>();

    private final String dataId = "gateway-routes.json";
    private final String group = "DEFAULT_GROUP";
    /**
     * 加载路由配置并初始化监听器监听路由配置的变化
     * */
    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        //1.项目启动时，先拉取一次配置（初始化路由表），并且添加配置监听器
        String configInfo = nacosConfigManager.getConfigService()
                .getConfigAndSignListener(dataId, group, 5000, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        //2.监听到配置变更，更新路由表（即更新一次配置）
                        updateConfigInfo(configInfo);
                    }
                });
        //3.第一次读取到配置，也需要更新到路由表
        updateConfigInfo(configInfo);
    }

    private void updateConfigInfo(String configInfo) {
        log.debug("监听到路由配置变更，{}", configInfo);
        //1.解析配置文件，转换成RouteDefinition对象列表
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        // 2.更新前先清空旧路由
        // 2.1.清除旧路由
        for (String routeId : routeIds) {
            writer.delete(Mono.just(routeId)).subscribe();
        }
        routeIds.clear();
        // 2.2.判断是否有新的路由要更新
        if (CollUtils.isEmpty(routeDefinitions)) {
            // 无新路由配置，直接结束
            return;
        }
        //3.更新路由表,mono是一个springboot提供的响应式编程的容器，里面会封装一个数据对象
        for (RouteDefinition routeDefinition : routeDefinitions) {
            //封装为Mono对象，调用save方法更新路由表,由于是异步执行，所以必须订阅
            writer.save(Mono.just(routeDefinition)).subscribe();
            // 3.2.记录路由id，方便将来删除
            routeIds.add(routeDefinition.getId());
        }
    }
}

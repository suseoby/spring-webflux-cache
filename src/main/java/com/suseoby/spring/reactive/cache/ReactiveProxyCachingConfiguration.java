package com.suseoby.spring.reactive.cache;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * @see org.springframework.cache.annotation.ProxyCachingConfiguration
 * @author seoby
 *
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ReactiveProxyCachingConfiguration extends AbstractCachingConfiguration implements ImportAware {

	@Nullable
	protected AnnotationAttributes enableCaching;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.enableCaching = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(EnableReactiveCaching.class.getName(), false));
		if (this.enableCaching == null) {
			throw new IllegalArgumentException(
					"@EnableReactiveCaching is not present on importing class " + importMetadata.getClassName());
		}
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public ReactiveCacheInterceptor reactiveCacheInterceptor() {
		ReactiveCacheInterceptor interceptor = new ReactiveCacheInterceptor();
		interceptor.configure(this.errorHandler, this.keyGenerator, this.cacheResolver, this.cacheManager);
		interceptor.setCacheOperationSource(reactiveCacheOperationSource());
		return interceptor;
	}

	@Bean(name = "org.springframework.cache.config.internalReactiveCacheAdvisor")
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public BeanFactoryCacheOperationSourceAdvisor reactiveCacheAdvisor() {
		BeanFactoryCacheOperationSourceAdvisor advisor = new BeanFactoryCacheOperationSourceAdvisor();
		advisor.setCacheOperationSource(reactiveCacheOperationSource());
		advisor.setAdvice(reactiveCacheInterceptor());

		if (this.enableCaching != null) {
			advisor.setOrder(this.enableCaching.<Integer>getNumber("order"));
		}

		return advisor;
	}

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CacheOperationSource reactiveCacheOperationSource() {
		return new AnnotationCacheOperationSource(new ReactiveCacheAnnotationParser());
	}
}

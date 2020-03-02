package com.suseoby.spring.reactive.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.util.StringUtils;

/**
 * @see org.springframework.cache.annotation.CachingConfigurationSelector
 * @author seoby
 *
 */
public class ReactiveCachingConfigurationSelector extends AdviceModeImportSelector<EnableReactiveCaching> {

	@Override
	public String[] selectImports(AdviceMode adviceMode) {
		switch (adviceMode) {
		case PROXY:
			return getProxyImports();
		case ASPECTJ:
			return getAspectJImports();
		default:
			return null;
		}
	}


	private String[] getProxyImports() {
		List<String> result = new ArrayList<>(2);
		result.add(AutoProxyRegistrar.class.getName());
		result.add(ReactiveProxyCachingConfiguration.class.getName());
		return StringUtils.toStringArray(result);
	}

	/**
	 * Return the imports to use if the {@link AdviceMode} is set to {@link AdviceMode#ASPECTJ}.
	 * <p>Take care of adding the necessary JSR-107 import if it is available.
	 */
	private String[] getAspectJImports() {
		List<String> result = new ArrayList<>(2);
		result.add("org.springframework.cache.aspectj.AspectJCachingConfiguration");
		return StringUtils.toStringArray(result);
	}
}

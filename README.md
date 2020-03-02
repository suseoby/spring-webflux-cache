# spring-webflux-cache

## Overview

provide reactive cache in Spring

## Supported Versions
* java : 1.8 or higher
* spring : 5.1.4-RELEASE or higher

## Usage
### pom.xml
	<dependency>
		<groupId>com.suseoby.spring</groupId>
		<artifactId>spring-webflux-cache</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>

OR, you can configure exclusions if conflict with your own project's version like below

	<dependency>
		<groupId>com.suseoby.spring</groupId>
		<artifactId>spring-webflux-cache</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<exclusions>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-context</artifactId>
			</exclusion>
			<exclusion>
				<groupId>org.springframework</groupId>
				<artifactId>spring-webflux</artifactId>
			</exclusion>
			<exclusion>
				<groupId>com.ncsoft.aframework.boot</groupId>
				<artifactId>aframework-boot-starter-logging-access</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

And, you should configure spring-boot-starter-cache dependency
And then, cache dependency which you want to use like ehcache, memcache and redis

	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-cache</artifactId>
	    <version>******</version>
	</dependency>

	<dependency>
        <groupId>net.sf.ehcache</groupId>
		<artifactId>ehcache</artifactId>
		<version>******</version>
	</dependency>

### Configuration
	@Configuration
	@EnableReactiveCaching
	public class CacheConfiguration {
	}

### Annotation on Method for cache
	// cache the flux result
	@ReactiveCacheable(value = "def", key = "'user-friend-'+#p0")
	public Flux<Map> findAllFriends(String userId){
		... ...
	}

	// cache the mono result using unless, The result does not be cached if unless condition is true
	@ReactiveCacheable(value = "def", key = "'user-follower-'+#p0", unless="#result == 100")
	public Mono<Integer> findGrade(String id){
		... ...
	}

	// remove the cached item
	@ReactiveCacheEvict(value = "def", key = "'user-follower-'+#p0")
	public void insertFollower(String id, Follower follower){
		... ...
	}
	
### log
if you would like to print log of cache found and put, you should set debug of "com.suseoby.spring.reactive.cache" package on your log configuration

#### Caution for use
* You can not use #result keywork in key attribute
* You can use both @ReactiveCacheEvict and @ReactiveCacheable on one method. but, Not guaranteed the execution order of two annotations 



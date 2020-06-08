# auto-detect-version

`auto-detect-version` 是一个用于自定检测pom文件中所依赖jar包最新版本的工具，用于快速检测版本中maven中心仓库中的最新版本，简化在maven中心仓库手动搜索。

当发现pom文件中的版本号和maven中心长裤不一致时，输出最新的相关信息。


## 特性

* 检测最新版本
* 检测大版本下的最新版本

## 使用教程

直接启用服务，通过sheel命令行方式启动。

`check E:\\git\\aliyun\\wueasy\\pom.xml`

* `check` 命名
* `E:\\git\\aliyun\\wueasy\\pom.xml` 为参数，可以是本地的pom文件绝对路径，或者http地址



## 执行效果

* `latest`：最新版本
* `largeLatest`：大版本下的最新版本

```
shell:>check E:\\git\\aliyun\\wueasy\\pom.xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version>
    <latest>8.0.20</latest>
    <largeLatest>5.1.49</largeLatest>
</dependency>

<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>4.3.0</version>
    <latest>5.0.0</latest>
    <largeLatest>4.3.0</largeLatest>
</dependency>

<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-client</artifactId>
    <version>4.3.0</version>
    <latest>5.0.0</latest>
    <largeLatest>4.3.0</largeLatest>
</dependency>

<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>4.3.0</version>
    <latest>5.0.0</latest>
    <largeLatest>4.3.0</largeLatest>
</dependency>

<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>3.14.9</version>
    <latest>4.7.2</latest>
    <largeLatest>3.14.9</largeLatest>
</dependency>

<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.5.8</version>
    <latest>3.6.1</latest>
    <largeLatest>3.6.1</largeLatest>
</dependency>

<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.9.1</version>
    <latest>3.10.0</latest>
    <largeLatest>3.10.0</largeLatest>
</dependency>

<dependency>
    <groupId>com.huaweicloud</groupId>
    <artifactId>esdk-obs-java</artifactId>
    <version>3.19.7</version>
    <latest>3.20.4.2</latest>
    <largeLatest>3.20.4.2</largeLatest>
</dependency>

检测成功！
shell:>
```



## License

```java
/*
 * auto-detect-version - Tools for automatically detecting the latest version of dependent packages in POM files.
 * Copyright (C) 2017-2020 wueasy.com , All Rights Reserved.

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

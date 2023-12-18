```
docker run --name nacos -e MODE=standalone -e NACOS_AUTH_IDENTITY_KEY=nacos -e NACOS_AUTH_IDENTITY_VALUE=nacos -e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 -e db_pool_config_connectionTimeout=60000 -p 8848:8848 -p 9848:9848 -d nacos/nacos-server:v2.2.3
```

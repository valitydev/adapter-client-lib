Hellgate Client

Обёртка над Hellgate для использования в адаптерах для провайдеров

### Настройки

Добавить в `pom.xml` в зависимости

```
<dependency>
    <groupId>com.rbkmoney.adapter-client-lib</groupId>
    <artifactId>hellgate-adapter-client</artifactId>
    <version>${hellgate-adapter-client.version}</version>
</dependency>
```

В зависимостях также должны быть указаны

```
<dependency>
    <groupId>com.rbkmoney.woody</groupId>
    <artifactId>woody-thrift</artifactId>
    <version>${woody-thrift.version}</version>
</dependency>
<dependency>
    <groupId>com.rbkmoney</groupId>
    <artifactId>damsel</artifactId>
    <version>${damsel.version}</version>
</dependency>
```

и в `application.yml`

```
hellgate:
    client:
        adapter:
            url: http://127.0.0.1:8022/v1/proxyhost/provider
            networkTimeout: 30000
```

При подключенной зависимости без указания настроек в `application.yml` и запуске приложения - оно выдаст ошибку, что не
был указан URL и как это исправить

### Использование

Для того, чтобы начать пользоваться библиотекой после подключения, необходимо просто добавить

```
@Autowired
HellgateAdapterClient client;
```

CDS Client Storage

Библиотека взаимодействия клиента с CDS сервисом для работы с приложениями

### Настройки

Добавить в `pom.xml` в зависимости

```
<dependency>
    <groupId>dev.vality.adapter-client-lib</groupId>
    <artifactId>cds-client-storage</artifactId>
    <version>${cds-client-storage.version}</version>
</dependency>
```

В зависимостях также должны быть указаны

```
<dependency>
    <groupId>dev.vality.woody</groupId>
    <artifactId>woody-thrift</artifactId>
    <version>${woody-thrift.version}</version>
</dependency>
<dependency>
    <groupId>dev.vality</groupId>
    <artifactId>damsel</artifactId>
    <version>${damsel.version}</version>
</dependency>
<dependency>
    <groupId>dev.vality</groupId>
    <artifactId>damsel</artifactId>
    <version>${damsel.version}</version>
</dependency>
```

и в `application.yml`

```
cds:
  client:
    storage:
      url: http://127.0.0.1:8022/v1/storage
      networkTimeout: 5000
```

При подключенной зависимости без указания настроек в `application.yml` и запуске приложения - оно выдаст ошибку, что не
был указан URL и как это исправить

### Использование

Для того, чтобы начать пользоваться библиотекой после подключения, необходимо просто добавить

```
@Autowired
CdsClientStorage cdsStorage;

CardData cardData = cdsStorage.getCardData(context);
SessionData sessionData = cdsStorage.getSessionData(context);
```

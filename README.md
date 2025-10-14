# Bank

yandex practicun bank project

## Запуск контейнеров

### Запуск контейнера Postgres

```
docker run --name postgres-17 -e POSTGRES_DB=bank -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v postgres-data:/var/lib/postgresql/data -d postgres:17
```

### Запуск контейнера Keycloack

```
docker run -d -p 8180:8080 --name keycloak -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.3 start-dev 
```

### Запуск контейнера Consul

```
docker run -d --name=consul-server -e CONSUL_BIND_INTERFACE=eth0 -p 8500:8500 -p 8600:8600/udp hashicorp/consul
```

Добавление общих параметров после запуска контейнера:

```
consul kv put config/management/endpoints/web/exposure/include "health,info"
consul kv put config/management/endpoint/health/show-details "always
```

## Генерация курсов валют  

Генерация курсов валют выполняется каждую минуту и первая генерация через минуту после старта микросервиса
exchange-generator.


## Запуск через helm

1. Сборка JAR
2. Сборка образа каждого сервиса:
```
docker build -t front-ui:0.0.1-SNAPSHOT .
```
3. Загрузка образа в minikube:
```
minikube image load front-ui:0.0.1-SNAPSHOT
```
4. Загрузка образа postgre в minikube:
```
docker pull bitnami/postgresql:latest
minikube image load bitnami/postgresql:latest
```
5. Установка через helm
```
helm install bank-practicum ./bank-charts/
```

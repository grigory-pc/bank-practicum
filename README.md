# Bank

yandex practicun bank project

## Запуск контейнеров  
### Запуск контейнера Postgres  
```
docker run --name postgres-17 -e POSTGRES_DB=bank -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v postgres-data:/var/lib/postgresql/data -d postgres:17
```

### Запуск контейнера Keycloack

```
docker run -d -p 8082:8080 --name keycloak -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.3 start-dev 
```

### Запуск контейнера Consul
```
docker run -d --name=consul-server -e CONSUL_BIND_INTERFACE=eth0 -p 8500:8500 -p 8600:8600/udp hashicorp/consul
```


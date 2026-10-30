# E-Commerce Gateway & Dashboard - Composite API

Práctica de Desarrollo Web Integral (Unidad 3). Una **Composite API** recibe una sola petición del cliente,
consulta en paralelo tres servicios (dos propios y uno externo) y responde un único JSON consolidado con el
detalle de un producto.

## Arquitectura

```
Cliente (Postman)
   │  GET /api/v1/product-summary/{id}
   ▼
cmp_products :9090  (compose, sin BD)
   │  Mono.zip: las tres peticiones salen al mismo tiempo
   ├──► base_products  :8081  GET /microservices/products/{id}          PostgreSQL products_db
   ├──► base_inventory :8082  GET /microservices/inventory/{productId}  PostgreSQL inventory_db
   └──► Frankfurter (externa)  https://api.frankfurter.dev/v1/latest?from=USD&to=MXN
```

| Carpeta | Tipo | Puerto | Responsabilidad |
|---|---|---|---|
| `base_products` | Propia (Spring Boot + JPA) | 8081 | Catálogo: nombre, descripción, categoría y precio en USD |
| `base_inventory` | Propia (Spring Boot + JPA) | 8082 | Inventario: unidades disponibles, ubicación y días de entrega |
| `cmp_products` | Propia, agregadora (Spring WebFlux) | 9090 | Orquesta las tres llamadas y unifica la respuesta |
| Frankfurter | Terceros | — | Tipo de cambio USD → MXN |

Los tres servicios propios escuchan solo en `127.0.0.1`.

## Requisitos

- Java 21
- PostgreSQL (probado con la versión 17) con el usuario `postgres`
- Conexión a Internet (para Frankfurter)

## 1. Crear las bases de datos

```bash
psql -U postgres -h localhost -c "CREATE DATABASE products_db;"
psql -U postgres -h localhost -c "CREATE DATABASE inventory_db;"
```

Las tablas y los datos de ejemplo se crean solos al arrancar cada servicio (`data.sql`).

## 2. Configurar las credenciales

En `base_products` y en `base_inventory`, copiar la plantilla y poner la contraseña de PostgreSQL:

```bash
cp base_products/src/main/resources/application-dev.properties.example base_products/src/main/resources/application-dev.properties
cp base_inventory/src/main/resources/application-dev.properties.example base_inventory/src/main/resources/application-dev.properties
```

`application-dev.properties` está en `.gitignore`: las credenciales nunca se suben.

## 3. Ejecutar

Cada servicio en su propia terminal, en este orden:

```bash
cd base_products && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd base_inventory && ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
cd cmp_products && ./mvnw spring-boot:run
```

Con Maven instalado también se puede usar `./run.sh` dentro de cada carpeta.

## 4. Probar

Datos de ejemplo:

| Producto | Nombre | Precio USD | Unidades |
|---|---|---|---|
| P-101 | Laptop Gamer XYZ | 1200.00 | 15 |
| P-102 | Monitor 27 pulgadas QHD | 320.00 | 0 |
| P-103 | Teclado mecánico RGB | 85.50 | 40 |

`GET http://127.0.0.1:9090/api/v1/product-summary/P-101` → **200**

```json
{
    "product_id": "P-101",
    "title": "Laptop Gamer XYZ",
    "price_usd": 1200.0,
    "price_mxn": 22497.0,
    "stock_status": "IN_STOCK",
    "available_units": 15,
    "currency_conversion_source": "Frankfurter External API",
    "status": "SUCCESS"
}
```

`price_mxn` cambia cada día con el tipo de cambio. `P-102` responde `"stock_status": "OUT_OF_STOCK"`.

### Manejo de errores

| Situación | Código | Respuesta |
|---|---|---|
| El producto no existe (`P-999`) | 404 | `{"status":"ERROR","message":"No existe el producto con id P-999"}` |
| Un servicio está apagado o tarda más de 5 s | 503 | `{"status":"ERROR","message":"El servicio <url> no responde"}` |
| Un servicio responde con otro error (p. ej. 500) | 502 | `{"status":"ERROR","message":"El servicio <url> respondió 500 INTERNAL_SERVER_ERROR"}` |

Para ver el 503: detener `base_inventory` y repetir la petición.

### Servicios individuales

- `GET http://127.0.0.1:8081/microservices/products/P-101`
- `GET http://127.0.0.1:8082/microservices/inventory/P-101`
- `GET https://api.frankfurter.dev/v1/latest?from=USD&to=MXN`

## Flujo de trabajo con Git

- `master`: versiones entregadas. `develop`: integración (rama por defecto).
- Una rama por funcionalidad, integrada a `develop` por Pull Request con *merge commit*:
  `feat/ram/base-products` (#1), `feat/ram/base-inventory` (#2), `feat/ram/cmp-products` (#3) y `docs/ram/readme`.
- Commits con Conventional Commits (`feat`, `chore`, `docs`).

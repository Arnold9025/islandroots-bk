# Backend Vision & Specifications

## Stack Technology
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Security**: Spring Security & JWT
- **Paiements**: Stripe
- **Livraison**: Shippo

## Architecture Globale
- **Type**: Clean Architecture Modulaire
- **Base Package**: `com.islandroots.bk.modules`

### Modules (Indépendants)
- `auth`: Gestion Authentication (Register, Login, JWT, Refresh Token).
- `user`: Gestion des Utilisateurs (Entité `User`, Rôles: USER, ADMIN).
- `product`: CRUD Produits, Catégories, Images, Stock.
- `cart`: Panier persistant (Add, Update Quantity, Remove Item).
- `order`: Le Cœur. Cycle de vie des commandes (PENDING -> PAID -> SHIPPED -> DELIVERED).
- `payment`: Intégration Stripe (Checkout Session, Webhooks).
- `shipping`: Intégration Shippo (Génération Web Label PDF, Tracking).
- `admin`: Gestion Produits, Commandes, Paiements, Impressions des Labels, Analytics.

## Entités Principales
- **User**: `id(UUID)`, `email(String)`, `password(String)`, `role(Role)`, `createdAt(LocalDateTime)`
- **Product**: `id(UUID)`, `name(String)`, `description(String)`, `price(BigDecimal)`, `stock(int)`
- **Cart**: `id(UUID)`, `userId(UUID)`, `items(List<CartItem>)`
- **Order**: `id(UUID)`, `userId(UUID)`, `status(OrderStatus)`, `total(BigDecimal)`, `items(List<OrderItem>)`
- **Shipment**: `id(UUID)`, `orderId(UUID)`, `labelUrl(String)`, `trackingNumber(String)`

## Webhooks et Sécurité Critiques
- **Paiement**: Traitement strict de `checkout.session.completed` (Webhook) pour passer l'Order à PAID.
- **Sécurité**: JWT Filter, BCrypt hasher, Rate limiting, Validation Inputs.

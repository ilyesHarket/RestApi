Objectif :
Dans cet exercice, vous allez étendre une API REST en Spring Boot en ajoutant des relations entre trois entités : Utilisateur (User), Produit (Product) et Catégorie (Category). Vous écrirez également des tests unitaires pour valider le bon fonctionnement de votre application.

Scénario :
Vous travaillez sur le backend d’une application e-commerce qui doit gérer des utilisateurs, des produits et des catégories.

Un utilisateur peut posséder plusieurs produits (relation one-to-many).
Un produit appartient à une seule catégorie (relation many-to-one).
Une catégorie peut contenir plusieurs produits (relation one-to-many).
L'API doit permettre d'assigner des produits aux utilisateurs et aux catégories.
Des tests unitaires doivent être rédigés pour vérifier les fonctionnalités des utilisateurs.
Tâches :
1️⃣ Définition du Modèle de Données
Créez une entité User avec les champs : id, name, email.
Modifiez l’entité Product pour inclure une relation many-to-one avec User et Category.
Créez une entité Category pour organiser les produits.
2️⃣ Implémentation des Endpoints API
Endpoints Utilisateur (UserController) :

Créer un utilisateur.
Récupérer tous les utilisateurs.
Récupérer un utilisateur par ID.
Assigner un produit à un utilisateur.
Endpoints Produit (ProductController) :

Créer un produit.
Récupérer tous les produits.
Récupérer un produit par ID.
Assigner un produit à une catégorie.
Endpoints Catégorie (CategoryController) :

Créer une catégorie.
Récupérer toutes les catégories.
Récupérer une catégorie par ID.
Défi Supplémentaire :
✅ Ajouter une validation (exemple : un email unique pour chaque utilisateur).
✅ Ajouter une fonctionnalité permettant de récupérer tous les produits appartenant à un utilisateur.
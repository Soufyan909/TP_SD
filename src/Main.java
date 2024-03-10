public class Main {
    // Structure pour représenter un tas
    static class Heap {
        static final int MAX_SIZE = 100;
        int[] array = new int[MAX_SIZE];
        int size;

        Heap(int[] array, int size) {
            this.array = array;
            this.size = size;
        }
    }

    // La fonction maxHeapify
    static void maxHeapify(Heap heap, int index) {
        int largest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        if (leftChild < heap.size && heap.array[leftChild] > heap.array[largest]) {
            largest = leftChild;
        }
        if (rightChild < heap.size && heap.array[rightChild] > heap.array[largest]) {
            largest = rightChild;
        }
        if (largest != index) {
            int temp = heap.array[index];
            heap.array[index] = heap.array[largest];
            heap.array[largest] = temp;
            maxHeapify(heap, largest);
        }
    }

    // La fonction buildMaxHeapify
    static void buildMaxHeap(Heap heap) {
        for (int i = (heap.size / 2) - 1; i >= 0; i--) {
            maxHeapify(heap, i);
        }
    }

    // La fonction heapSort
    static void heapSort(int[] array, int size) {
        Heap heap = new Heap(array, size);
        buildMaxHeap(heap);
        for (int i = size - 1; i > 0; i--) {
            int temp = heap.array[0];
            heap.array[0] = heap.array[i];
            heap.array[i] = temp;
            heap.size--;
            maxHeapify(heap, 0);
        }
        System.arraycopy(heap.array, 0, array, 0, size);
    }

    // Définition de la structure d'un noeud de l'arbre
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        int height;

        TreeNode(int data) {
            this.data = data;
            left = null;
            right = null;
            height = 1;
        }
    }

    // Fonction pour créer un nouveau noeud
    static TreeNode createNode(int data) {
        return new TreeNode(data);
    }

    // Fonction pour le parcours infixé
    static void parcoursInfixe(TreeNode root) {
        if (root != null) {
            parcoursInfixe(root.left);
            System.out.print(root.data + " ");
            parcoursInfixe(root.right);
        }
    }

    // Fonction pour rechercher un élement quelconque
    static boolean trouver(TreeNode root, int key) {
        if (root == null || root.data == key) {
            return root != null;
        }
        if (key < root.data) {
            return trouver(root.left, key);
        } else {
            return trouver(root.right, key);
        }
    }

    // Fonction pour trouver l'élément min
    static int trouverMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root.data;
    }

    // Fonction pour trouver l'élément max
    static int trouverMax(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root.data;
    }

    // Fonction pour insérer un nouvel élément
    static TreeNode inserer(TreeNode root, int data) {
        if (root == null) {
            return createNode(data);
        }
        if (data <= root.data) {
            root.left = inserer(root.left, data);
        } else {
            root.right = inserer(root.right, data);
        }
        return root;
    }

    // Fonction pour trouver le noeud successeur
    static TreeNode trouverNoeudSucc(TreeNode node) {
        TreeNode current = node;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Fonction pour trouver le noeud prédecesseur
    static TreeNode trouverNoeudPred(TreeNode node) {
        TreeNode current = node;
        while (current != null && current.right != null) {
            current = current.right;
        }
        return current;
    }

    // Fonction pour supprimer un élément
    static TreeNode supprimer(TreeNode root, int key) {
        if (root == null) return root;
        if (key < root.data) {
            root.left = supprimer(root.left, key);
        } else if (key > root.data) {
            root.right = supprimer(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            TreeNode temp = trouverNoeudSucc(root.right);
            root.data = temp.data;
            root.right = supprimer(root.right, temp.data);
        }
        return root;
    }

    // Fonction pour récuperer la hauteur
    static int hauteur(TreeNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Fonction pour vérifier si un arbre est un AVL
    static boolean estAVL(TreeNode root) {
        if (root == null) return true;
        int diff = Math.abs(hauteur(root.left) - hauteur(root.right));
        return diff <= 1 && estAVL(root.left) && estAVL(root.right);
    }

    // Fonction pour effectuer une rotation à gauche
    static TreeNode rotationGauche(TreeNode y) {
        TreeNode x = y.right;
        TreeNode i = x.left;
        x.left = y;
        y.right = i;

        y.height = Math.max(hauteur(y.left), hauteur(y.right)) + 1;
        x.height = Math.max(hauteur(x.left), hauteur(x.right)) + 1;
        return x;
    }

    // Fonction pour effectuer une rotation à droite
    static TreeNode rotationDroite(TreeNode y) {
        TreeNode x = y.left;
        TreeNode i = x.right;
        y.left = x;
        x.right = y;
        x.height = Math.max(hauteur(x.left), hauteur(x.right)) + 1;
        y.height = Math.max(hauteur(y.left), hauteur(y.right)) + 1;
        return y;
    }

    // Fonction pour calculer le facteur d'équilibre d'un noeud
    static int balance(TreeNode node) {
        return (node == null) ? 0 : hauteur(node.left) - hauteur(node.right);
    }

    // Fonction pour équilibrer un arbre
    static TreeNode equilibrer(TreeNode root) {
        if (root == null) return root;
        root.left = equilibrer(root.left);
        root.right = equilibrer(root.right);

        int diff = balance(root);

        if (diff > 1 && balance(root.left) >= 0) {
            return rotationDroite(root);
        }

        if (diff > 1 && balance(root.left) < 0) {
            root.left = rotationGauche(root.left);
            return rotationDroite(root);
        }

        if (diff < -1 && balance(root.right) <= 0) {
            return rotationGauche(root);
        }

        if (diff < -1 && balance(root.right) > 0) {
            root.right = rotationDroite(root.right);
            return rotationGauche(root);
        }

        return root;
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        TreeNode root = createNode(10);
        root.left = createNode(5);
        root.right = createNode(15);
        root.left.left = createNode(3);
        root.left.right = createNode(7);
        root.right.left = createNode(12);
        root.right.right = createNode(18);

        // Affichage de l'arbre avec parcours infixé
        System.out.print("Parcours Infixé: ");
        parcoursInfixe(root);
        System.out.println();

        // Recherche d'un élément
        int searchKey = 7;
        System.out.println("Recherche de " + searchKey + ": " + (trouver(root, searchKey) ? "Présent" : "Absent"));

        // Suppression d'un élément
        int deleteKey = 12;
        root = supprimer(root, deleteKey);
        System.out.print("Parcours Infixé après suppression de " + deleteKey + ": ");
        parcoursInfixe(root);
        System.out.println();

        // Vérification si l'arbre est AVL
        System.out.println("L'arbre est un AVL: " + estAVL(root));

        // Tri d'un tableau avec HeapSort
        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.print("Tableau avant tri: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
        heapSort(arr, arr.length);
        System.out.print("Tableau après tri: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

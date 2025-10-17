// Importaciones requeridas
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Categories {

    // Clase de categoria con la estructura de id, nombre y subcategorias 
    public static class Category {
        public final int id;
        public final String name;
        public final List<Category> subcategories;

        public Category(int id, String name, List<Category> subcategories) {
            this.id = id;
            this.name = name;
            this.subcategories = subcategories;
        }

    }

    // Clase auxiliar que define estructura para el nodo y la ruta acumulada
    public static class NodePath {
        final Category node; // Categoria actual
        final String path;   // Ruta hasta ese nodo

        NodePath(Category node, String path) {
            this.node = node;
            this.path = path;
        }
    }

    // Parte 1
    public static List<String> getCategoryPaths(Category root){
        List<String> result = new ArrayList<>();
        
        // Caso si es nula la categoria
        if(root == null) return result;

        //Ruta inicial
        Deque<NodePath> stack = new ArrayDeque<>();
        stack.push(new NodePath(root, root.name));

        while(!stack.isEmpty()){
            // Obtener el node actual
            NodePath np = stack.pop();
            Category node = np.node;

            // Si no tiene subcategorias añadir ruta completa al resultado
            if (node.subcategories == null || node.subcategories.isEmpty()) {
                result.add(np.path);
            } else {
                // Si tiene subcategorías recorrer los hijos al reves
                for (int i = node.subcategories.size() - 1; i >= 0; i--){
                    Category child = node.subcategories.get(i);
                    stack.push(new NodePath(child,np.path + "/" + child.name));
                }
            }
        }
        return result;
    }

    // Parte 2
    public static Category findCategory(Category root, int targetId){
        // Caso nulo
        if(root == null) return null;

        Deque<Category> stack = new ArrayDeque<>();
        stack.push(root);

        // Recorrer toda la categoría
        while (!stack.isEmpty()) {
            Category current = stack.pop();

            // Si es la categoria acutal regresarla
            if(current.id == targetId) {
                return current;
            }

            // Si tiene subcategorías se agregan al stack
            if(current.subcategories != null){
                for(Category child : current.subcategories){
                    stack.push(child);
                }
            }
            
        }
        // regresa nulo si no existe ese id
        return null;
    }

}


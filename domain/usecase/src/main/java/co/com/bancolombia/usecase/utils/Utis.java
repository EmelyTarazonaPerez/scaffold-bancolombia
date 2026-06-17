package co.com.bancolombia.usecase.utils;

import co.com.bancolombia.model.exception.Exceptions;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;

public class Utis {

   public static final String INVALID_FRANCHISE_NAME = "INVALID_FRANCHISE_NAME";
   public static final String DUPLICATE_FRANCHISE_NAME = "DUPLICATE_FRANCHISE_NAME";
   public static final String  ERROR_SAME_NAME = "A franchise with the same name already exists";
   public static final String  ERROR_FRANCHISE_NOT_FOUND = "Franchise not found";
   public static final String ERROR_NAME_REQUIRED = "Franchise name is required";

   public static Product findProduct (Franchise franchise, String branchId, String productId) {
       return franchise.getBranches()
               .stream()
               .filter(branch -> branch.getId().equals(branchId))
               .flatMap(branch -> branch.getProducts().stream())
               .filter(product -> product.getId().equals(productId))
               .findFirst()
               .orElseThrow(Exceptions::productNotFound);
    }
}

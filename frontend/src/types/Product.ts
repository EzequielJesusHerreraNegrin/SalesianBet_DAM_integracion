import productState from "./enums/ProductState";

type Product = {
  productId: number;
  productName: string;
  productImage: string;
  state?: productState;
  price: number;
};

type ProductRequest = {
  productName: string;
  productImage: string;
  state: productState;
  price: number;
};

interface ProductCartItemResponseDto {
  productId: number;
  productName: string;
  productImage: string;
  price: number;
}

type ProductResponse = {
  productId: number;
  productName: string;
  productImage: string;
  state?: productState;
  buys?: number;
  price: number;
};

type ProductPurchaseResponseDto = {
  productId: string;
  productName: string;
  productImage: string;
  price: number;
};

export type {
  Product,
  ProductPurchaseResponseDto,
  ProductRequest,
  ProductResponse,
  ProductCartItemResponseDto,
};

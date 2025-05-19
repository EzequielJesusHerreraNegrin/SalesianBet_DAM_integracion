import { UserEntityResponseDto } from "../service/CartItem.service";
import productState from "./enums/ProductState";

type Product = {
  productId: number;
  productName: string;
  imageImage: string;
  state?: productState;
  price: number;
};

type ProductRequest = {
  productName: string;
  imageImage: string;
  state: productState;
  price: number;
};

type ProductResponse = {
  productId: number;
  productName: string;
  imageImage: string;
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
  ProductRequest,
  ProductResponse,
  CartItemResponse as ProductCartItem,
  ProductPurchaseResponseDto,
};

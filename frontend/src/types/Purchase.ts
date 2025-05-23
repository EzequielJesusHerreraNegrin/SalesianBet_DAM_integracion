import { ProductPurchaseResponseDto } from "./Product";

export type Purchase = {
  purchaseId: number;
  product: ProductPurchaseResponseDto;
  quantity: number;
  totalPrice: number;
  purchaseDate: string;
};

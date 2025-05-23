import api from "../service/api";
import { ApiResponseDto } from "../types/api";
import { ProductRequest, ProductResponse } from "../types/Product";

const createProduct = async (productRequest: ProductRequest) => {
  const response = await api.post(`/products`, productRequest);
  return response.data;
};

const getAllProducts = async () => {
  const response = await api.get<ApiResponseDto<ProductResponse[]>>(
    `/products`
  );
  return response.data.data;
};

const getProductById = async (productId: number) => {
  const response = await api.get<ProductResponse>(`/products/${productId}`);
  return response.data;
};

const updateProduct = async (
  productId: number,
  productRequest: ProductRequest
) => {
  const response = await api.put<ProductResponse>(
    `/products/${productId}`,
    productRequest
  );
  return response.data;
};

const ProductService = {
  createProduct,
  getAllProducts,
  getProductById,
  updateProduct,
};

export default ProductService;

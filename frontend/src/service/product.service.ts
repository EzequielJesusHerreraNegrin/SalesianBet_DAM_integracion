import axios from "axios";
import { ProductResponse, ProductRequest } from "../types/Product";
import { API_URL } from "./match.service";
import { ApiResponseDto } from "./CartItem.service";

const createProduct = async (productRequest: ProductRequest) => {
  const response = await axios.post(`${API_URL}/products`, productRequest);
  return response.data;
};

const getAllProducts = async () => {
  const response = await axios.get<ApiResponseDto<ProductResponse[]>>(
    `${API_URL}/products`
  );
  return response.data.data; // Asegúrate de que la respuesta tenga la estructura correcta
};

const getProductById = async (productId: number) => {
  const response = await axios.get<ProductResponse>(
    `${API_URL}/products/${productId}`
  );
  return response.data;
};

const updateProduct = async (
  productId: number,
  productRequest: ProductRequest
) => {
  const response = await axios.put<ProductResponse>(
    `${API_URL}/products/${productId}`,
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

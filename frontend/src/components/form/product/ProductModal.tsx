import { useEffect, useState } from "react";
import {
  Modal,
  Box,
  TextField,
  Button,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  SelectChangeEvent,
} from "@mui/material";
import { Product, ProductRequest } from "../../../types/Product";
import productState from "../../../types/enums/ProductState"; // Asegúrate que la ruta es correcta

interface ProductModalProps {
  open: boolean;
  onClose: () => void;
  onSubmit: (productData: ProductRequest, productId?: number) => Promise<void>;
  initialData?: Product | null;
}

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  display: "flex",
  flexDirection: "column",
  gap: 2,
};

const ProductModal: React.FC<ProductModalProps> = ({
  open,
  onClose,
  onSubmit,
  initialData,
}) => {
  const [formData, setFormData] = useState<ProductRequest>({
    productName: "",
    productImage: "",
    price: 0,
    state: productState.PUBLIC, // Default state
  });
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (initialData) {
      setFormData({
        productName: initialData.productName ?? "",
        productImage: initialData.productImage ?? "",
        price: initialData.price ?? 0,
        state: initialData.state ?? productState.PUBLIC,
      });
    } else {
      // Reset for new product
      setFormData({
        productName: "",
        productImage: "",
        price: 0,
        state: productState.PUBLIC,
      });
    }
  }, [initialData, open]); // Re-run if initialData or open status changes

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "price" ? parseFloat(value) || 0 : value,
    }));
  };

  const handleSelectChange = (event: SelectChangeEvent<productState>) => {
    setFormData((prev) => ({
      ...prev,
      state: event.target.value as productState,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      await onSubmit(formData, initialData?.productId);
      onClose(); // Close modal on successful submission
    } catch (error) {
      console.error("Error submitting product:", error);
      // Aquí podrías mostrar un mensaje de error al usuario
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="product-modal-title"
      aria-describedby="product-modal-description"
    >
      <Box sx={style} component="form" onSubmit={handleSubmit}>
        <Typography id="product-modal-title" variant="h6" component="h2">
          {initialData ? "Editar Producto" : "Crear Nuevo Producto"}
        </Typography>
        <TextField
          label="Nombre del Producto"
          name="productName"
          value={formData.productName}
          onChange={handleChange}
          required
          fullWidth
        />
        <TextField
          label="URL de la Imagen"
          name="imageImage"
          value={formData.productImage}
          onChange={handleChange}
          required
          fullWidth
        />
        <TextField
          label="Precio"
          name="price"
          type="number"
          value={formData.price}
          onChange={handleChange}
          required
          fullWidth
          inputProps={{ min: 0, step: "0.01" }}
        />
        <FormControl fullWidth required>
          <InputLabel id="state-select-label">Estado</InputLabel>
          <Select
            labelId="state-select-label"
            id="state-select"
            name="state"
            value={formData.state}
            label="Estado"
            onChange={handleSelectChange}
          >
            {Object.values(productState).map((s) => (
              <MenuItem key={s} value={s}>
                {s}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Box
          sx={{ display: "flex", justifyContent: "flex-end", gap: 1, mt: 2 }}
        >
          <Button onClick={onClose} color="secondary" disabled={isLoading}>
            Cancelar
          </Button>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            disabled={isLoading}
          >
            {isLoading ? "Guardando..." : initialData ? "Actualizar" : "Crear"}
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default ProductModal;

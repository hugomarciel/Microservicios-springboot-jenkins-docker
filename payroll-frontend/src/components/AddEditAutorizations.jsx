import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import autorizationService from "../services/autorization.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";

const AddEditAutorizations = () => {
  const [rutEmployee, setRutEmployee] = useState("");
  const [authorizedHours, setAuthorizedHours] = useState("");
  const [document, setDocument] = useState(null);
  const [date, setDate] = useState("");
  const [reason, setReason] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState("Nueva Autorización");

  const fetchData = async () => {
    if (id) {
      setLoading(true);
      setTitle("Editar Autorización");
      try {
        const response = await autorizationService.get(id);
        const data = response.data;
        setRutEmployee(data.rutEmployee);
        setDate(data.date);
        setReason(data.reason);
        setAuthorizedHours(data.authorizedHours);
        setDocument(data.document || "");
      } catch (err) {
        setError("Error al cargar los datos.");
      } finally {
        setLoading(false);
      }
    }
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  const validateForm = () => {
    if (!rutEmployee || !date || !reason || !authorizedHours) {
      setError("Todos los campos obligatorios deben completarse.");
      return false;
    }
    setError(null);
    return true;
  };

  const saveAuthorization = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const authorization = { rutEmployee, date, reason, authorizedHours, document, id };
    setLoading(true);
    try {
      if (id) {
        await autorizationService.update(authorization);
        console.log("Autorización actualizada.");
      } else {
        await autorizationService.create(authorization);
        console.log("Autorización creada.");
      }
      navigate("/autorization/list");
    } catch (err) {
      setError("Ocurrió un error al guardar los datos.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
    >
      <h3>{title}</h3>
      <hr />

      {loading ? (
        <CircularProgress />
      ) : (
        <>
          {error && <Alert severity="error">{error}</Alert>}

          <FormControl fullWidth margin="normal">
            <TextField
              id="rutEmployee"
              label="RUT del Empleado"
              value={rutEmployee}
              onChange={(e) => setRutEmployee(e.target.value)}
              helperText="Ejemplo: 12.587.698-8"
              required
            />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              id="date"
              label="Fecha"
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              InputLabelProps={{ shrink: true }}
              required
            />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              id="reason"
              label="Razón"
              value={reason}
              onChange={(e) => setReason(e.target.value)}
              helperText="Motivo de la autorización"
              required
            />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              id="authorizedHours"
              label="Horas Autorizadas"
              type="number"
              value={authorizedHours}
              onChange={(e) => setAuthorizedHours(e.target.value)}
              helperText="Ingrese la cantidad de horas"
              required
            />
          </FormControl>

          <FormControl fullWidth margin="normal">
            <TextField
              id="document"
              label="Documento"
              value={document || ""}
              onChange={(e) => setDocument(e.target.value)}
              helperText="Adjunte un documento (opcional)"
            />
          </FormControl>

          <Box display="flex" justifyContent="space-between" width="100%" mt={2}>
            <Button variant="outlined" color="secondary" onClick={() => navigate("/autorization/list")}>
              Cancelar
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={saveAuthorization}
              startIcon={<SaveIcon />}
            >
              Guardar
            </Button>
          </Box>
        </>
      )}
    </Box>
  );
};

export default AddEditAutorizations;

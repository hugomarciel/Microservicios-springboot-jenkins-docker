import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import justificationService from "../services/justification.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";

const AddEditJustifications = () => {
  const [rutEmployee, setRutEmployee] = useState("");
  const [motivation, setMotivation] = useState("");
  const [document, setDocument] = useState(null);
  const [date, setDate] = useState("");
  const { id } = useParams();
  const [titleForm, setTitleForm] = useState("Nueva Justificación");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      setTitleForm("Editar Justificación");
      setLoading(true);
      justificationService
        .get(id)
        .then((response) => {
          setRutEmployee(response.data.rutEmployee);
          setDate(response.data.date);
          setMotivation(response.data.motivation);
          setDocument(response.data.document || "");
        })
        .catch(() => setError("Error al cargar los datos."))
        .finally(() => setLoading(false));
    }
  }, [id]);

  const validateForm = () => {
    if (!rutEmployee || !date || !motivation) {
      setError("Todos los campos obligatorios deben completarse.");
      return false;
    }
    setError(null);
    return true;
  };

  const saveJustification = (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    const justification = { rutEmployee, date, motivation, document, id };
    setLoading(true);

    if (id) {
      justificationService
        .update(justification)
        .then(() => navigate("/justification/list"))
        .catch(() => setError("Ocurrió un error al actualizar la justificación."))
        .finally(() => setLoading(false));
    } else {
      justificationService
        .create(justification)
        .then(() => navigate("/justification/list"))
        .catch(() => setError("Ocurrió un error al crear la justificación."))
        .finally(() => setLoading(false));
    }
  };

  return (
    <Box display="flex" flexDirection="column" alignItems="center" component="form">
      <h3>{titleForm}</h3>
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
              helperText="Ejemplo: 12.345.678-9"
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
              id="motivation"
              label="Motivación"
              value={motivation}
              onChange={(e) => setMotivation(e.target.value)}
              helperText="Motivo de la justificación"
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
            <Button
              variant="outlined"
              color="secondary"
              onClick={() => navigate("/justification/list")}
            >
              Cancelar
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={saveJustification}
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

export default AddEditJustifications;


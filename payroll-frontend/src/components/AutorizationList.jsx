import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import autorizationService from "../services/autorization.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import { CircularProgress, Alert, Typography } from "@mui/material";

const AutorizationList = () => {
  const [autorizations, setAutorizations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Función para inicializar los datos
  const init = () => {
    setLoading(true);
    setError("");
    autorizationService
      .getAll()
      .then((response) => {
        console.log("Cargando autorizaciones...", response.data);
        setAutorizations(response.data);
      })
      .catch((error) => {
        console.error("Error al cargar las autorizaciones:", error);
        setError("No se pudieron cargar las autorizaciones. Intente de nuevo.");
      })
      .finally(() => setLoading(false));
  };

  // Hook de efecto para inicializar datos al montar el componente
  useEffect(() => {
    init();
  }, []);

  // Manejo de eliminación
  const handleDelete = (id) => {
    const confirmDelete = window.confirm(
      "¿Está seguro que desea borrar esta Autorización?"
    );
    if (confirmDelete) {
      autorizationService
        .remove(id)
        .then(() => {
          console.log("Autorización eliminada.");
          init();
        })
        .catch((error) => {
          console.error("Error al eliminar la autorización:", error);
          setError("No se pudo eliminar la autorización. Intente nuevamente.");
        });
    }
  };

  // Navegación a la edición
  const handleEdit = (id) => {
    navigate(`/autorization/edit/${id}`);
  };

  return (
    <div>
      {/* Título y descripción */}
      <Typography variant="h4" align="center" gutterBottom>
        Autorización de Horas Extra
      </Typography>
      <Typography variant="body1" align="center" paragraph>
        Gestiona las autorizaciones de horas extra registradas.
      </Typography>

      {/* Botón para agregar nueva autorización */}
      <div style={{ display: "flex", justifyContent: "center", marginBottom: "1rem" }}>
        <Link to="/autorization/add" style={{ textDecoration: "none" }}>
          <Button
            variant="contained"
            color="primary"
            startIcon={<AddCircleOutlineIcon />}
          >
            Ingresar Nueva Autorización
          </Button>
        </Link>
      </div>

      {/* Mensaje de error */}
      {error && (
        <Alert severity="error" style={{ marginBottom: "1rem" }}>
          {error}
        </Alert>
      )}

      {/* Indicador de carga */}
      {loading ? (
        <div style={{ display: "flex", justifyContent: "center" }}>
          <CircularProgress />
        </div>
      ) : (
        // Tabla de datos
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} size="small" aria-label="tabla de autorizaciones">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Rut Empleado
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Fecha
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Motivo
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Horas Autorizadas
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Acciones
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {autorizations.map((autorization) => (
                <TableRow key={autorization.id}>
                  <TableCell align="left">{autorization.rutEmployee}</TableCell>
                  <TableCell align="left">{autorization.date}</TableCell>
                  <TableCell align="left">{autorization.reason}</TableCell>
                  <TableCell align="left">{autorization.authorizedHours}</TableCell>
                  <TableCell align="left">
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(autorization.id)}
                      startIcon={<EditIcon />}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(autorization.id)}
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<DeleteIcon />}
                    >
                      Eliminar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </div>
  );
};

export default AutorizationList;

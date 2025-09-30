import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
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
import { Alert, CircularProgress, Typography } from "@mui/material";
import justificationService from "../services/justification.service";

const JustificationList = () => {
  const [justifications, setJustifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const init = () => {
    setLoading(true);
    setError("");
    justificationService
      .getAll()
      .then((response) => {
        setJustifications(response.data);
      })
      .catch((error) => {
        console.error("Error al obtener las justificaciones:", error);
        setError("Error al cargar las justificaciones. Intente de nuevo.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("¿Está seguro que desea borrar esta Justificación?")) {
      setError("");
      justificationService
        .remove(id)
        .then(() => {
          init();
        })
        .catch((error) => {
          console.error("Error al eliminar la justificación", error);
          setError("No se pudo eliminar la justificación. Intente nuevamente.");
        });
    }
  };

  const handleEdit = (id) => {
    navigate(`/justification/edit/${id}`);
  };

  return (
    <div>
      <Typography variant="h4" align="center" gutterBottom>
        Justificación de Atrasos
      </Typography>
      <Typography variant="body1" align="center" gutterBottom>
        Aquí puedes ver, editar o eliminar las justificaciones registradas de atrasos.
      </Typography>

      <div style={{ display: "flex", justifyContent: "center", marginBottom: "1rem" }}>
        <Link to="/justification/add" style={{ textDecoration: "none" }}>
          <Button
            variant="contained"
            color="primary"
            startIcon={<AddCircleOutlineIcon />}
          >
            Ingresar Nueva Justificación
          </Button>
        </Link>
      </div>

      {error && (
        <Alert severity="error" style={{ marginBottom: "1rem" }}>
          {error}
        </Alert>
      )}

      {loading ? (
        <div style={{ display: "flex", justifyContent: "center" }}>
          <CircularProgress />
        </div>
      ) : (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} size="small" aria-label="tabla de justificaciones">
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
                  Documento
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Acciones
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {justifications.map((justification) => (
                <TableRow key={justification.id}>
                  <TableCell align="left">{justification.rutEmployee}</TableCell>
                  <TableCell align="left">{justification.date}</TableCell>
                  <TableCell align="left">{justification.motivation}</TableCell>
                  <TableCell align="left">{justification.document || "N/A"}</TableCell>
                  <TableCell align="left">
                    <Button
                      variant="contained"
                      color="info"
                      size="small"
                      onClick={() => handleEdit(justification.id)}
                      startIcon={<EditIcon />}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => handleDelete(justification.id)}
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

export default JustificationList;

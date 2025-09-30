import { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
  Button,
  Grid,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Typography,
  CircularProgress,
} from "@mui/material";
import paycheckService from "../services/paycheck.service";

const PaycheckList = () => {
  const [paychecks, setPaychecks] = useState([]);
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchPaychecks = () => {
    setLoading(true);
    setError("");
    paycheckService
      .getAll()
      .then((response) => {
        setPaychecks(response.data);
      })
      .catch(() => {
        setError("Error al cargar la planilla de sueldos.");
      })
      .finally(() => setLoading(false));
  };

  const handleCalculate = () => {
    setLoading(true);
    setError("");
    paycheckService
      .calculatePaycheck(year, month)
      .then(() => fetchPaychecks())
      .catch(() => {
        setError("Error al calcular los sueldos.");
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchPaychecks();
  }, []);

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Planilla de Sueldos
      </Typography>

      <Grid container spacing={2} alignItems="center">
        <Grid item>
          <FormControl>
            <InputLabel>Año</InputLabel>
            <Select value={year} onChange={(e) => setYear(e.target.value)} label="Año">
              {[2022, 2023, 2024, 2025].map((yearOption) => (
                <MenuItem key={yearOption} value={yearOption}>
                  {yearOption}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item>
          <FormControl>
            <InputLabel>Mes</InputLabel>
            <Select value={month} onChange={(e) => setMonth(e.target.value)} label="Mes">
              {[...Array(12).keys()].map((index) => (
                <MenuItem key={index} value={index + 1}>
                  {new Date(0, index).toLocaleString("es-CL", { month: "long" })}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item>
          <Button
            variant="contained"
            color="primary"
            onClick={handleCalculate}
            disabled={loading}
          >
            {loading ? <CircularProgress size={20} /> : "Calcular"}
          </Button>
        </Grid>
        <Grid item>
          <Button
            variant="outlined"
            onClick={fetchPaychecks}
            disabled={loading}
          >
            Actualizar
          </Button>
        </Grid>
      </Grid>

      {loading && (
        <Typography variant="body1" color="textSecondary" sx={{ mt: 2 }}>
          Cargando...
        </Typography>
      )}

      {error && (
        <Typography variant="body1" color="error" sx={{ mt: 2 }}>
          {error}
        </Typography>
      )}

      <TableContainer component={Paper} sx={{ mt: 3 }}>
        <Table size="small" aria-label="Planilla de sueldos">
          <TableHead>
            <TableRow>
              {[
                "Año",
                "Mes",
                "RUT",
                "Nombre",
                "Apellidos",
                "Categoría",
                "Años de servicio",
                "Sueldo Fijo",
                "Bonif. Años",
                "Bonif. Horas Extra",
                "Descuentos",
                "Sueldo Bruto",
                "Cotización Previsional",
                "Cotización Salud",
                "Sueldo Final",
              ].map((header) => (
                <TableCell key={header} align="right" sx={{ fontWeight: "bold" }}>
                  {header}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {paychecks.length === 0 ? (
              <TableRow>
                <TableCell colSpan={15} align="center">
                  No hay datos disponibles.
                </TableCell>
              </TableRow>
            ) : (
              paychecks.map((paycheck) => (
                <TableRow key={paycheck.id}>
                  {[
                    paycheck.year,
                    paycheck.month,
                    paycheck.rut,
                    paycheck.name,
                    paycheck.lastName,
                    paycheck.category,
                    paycheck.servicesYears,
                    paycheck.monthSalary,
                    paycheck.servicesYearsBonus,
                    paycheck.extraHoursBonus,
                    paycheck.discounts,
                    paycheck.grossSalary,
                    paycheck.forecastQuote,
                    paycheck.healthQuote,
                    paycheck.totalSalary,
                  ].map((value, index) => (
                    <TableCell key={index} align="right">
                      {new Intl.NumberFormat("es-CL").format(value)}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default PaycheckList;

import React, { useState } from "react";
import InAndOutRegService from "../services/inandoutreg.service"; // Asegúrate de que la ruta sea correcta
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Alert from "@mui/material/Alert";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Paper from "@mui/material/Paper";

const InAndOutReg = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file && file.type === "text/plain") {
            setSelectedFile(file);
            setError("");
            setMessage("");
        } else {
            setSelectedFile(null);
            setError("Por favor, selecciona un archivo .txt válido.");
        }
    };

    const handleFileUpload = async (e) => {
        e.preventDefault();

        if (!selectedFile) {
            setError("No se ha seleccionado ningún archivo.");
            return;
        }

        try {
            const formData = new FormData();
            formData.append("file", selectedFile);

            await InAndOutRegService.uploadFile(formData);
            setMessage("Archivo registrado exitosamente.");
            setError("");
            setSelectedFile(null);
        } catch (error) {
            console.error("Error al subir el archivo:", error);
            setError("Error al subir el archivo, verifica el formato o inténtalo nuevamente.");
            setMessage("");
        }
    };

    const handleClearFile = () => {
        setSelectedFile(null);
        setMessage("");
        setError("");
    };

    return (
        <Box component={Paper} p={3} mt={4} maxWidth={500} mx="auto">
            <Typography variant="h5" mb={2} textAlign="center">
                Subir Archivo de Asistencia
            </Typography>

            <form onSubmit={handleFileUpload}>
                <Box mb={2}>
                    <TextField
                        type="file"
                        fullWidth
                        inputProps={{ accept: ".txt" }}
                        onChange={handleFileChange}
                        helperText="Solo archivos de texto (.txt) son permitidos."
                    />
                </Box>

                {error && (
                    <Alert severity="error" style={{ marginBottom: "1rem" }}>
                        {error}
                    </Alert>
                )}

                {message && (
                    <Alert severity="success" style={{ marginBottom: "1rem" }}>
                        {message}
                    </Alert>
                )}

                <Box display="flex" justifyContent="space-between" mt={2}>
                    <Button
                        variant="contained"
                        color="primary"
                        type="submit"
                        disabled={!selectedFile}
                    >
                        Subir Archivo
                    </Button>
                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={handleClearFile}
                        disabled={!selectedFile}
                    >
                        Limpiar Selección
                    </Button>
                </Box>
            </form>
        </Box>
    );
};

export default InAndOutReg;

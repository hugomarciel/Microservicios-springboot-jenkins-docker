import React, { useState } from "react";
import LateArrivalsService from "../services/latearrivals.service"; 

const CalculateLateArrivals = () => {
    const [year, setYear] = useState("");
    const [month, setMonth] = useState("");
    const [message, setMessage] = useState("");

    const handleCalculate = async (e) => {
        e.preventDefault();
        try {
            await LateArrivalsService.calculateLateArrivals(year, month);
            setMessage("Cálculo de minutos de atraso realizado exitosamente.");
        } catch (error) {
            console.error("Error al calcular los minutos de atraso:", error);
            setMessage("Error al calcular los minutos de atraso.");
        }
    };

    return (
        <div>
            <h2>Calcular Minutos de Atraso</h2>
            <form onSubmit={handleCalculate}>
                <input
                    type="number"
                    value={year}
                    onChange={(e) => setYear(e.target.value)}
                    placeholder="Año"
                    required
                />
                <input
                    type="number"
                    value={month}
                    onChange={(e) => setMonth(e.target.value)}
                    placeholder="Mes"
                    required
                />
                <button type="submit">Calcular</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default CalculateLateArrivals;

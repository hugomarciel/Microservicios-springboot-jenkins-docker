// CalculateExtraHours.jsx

import React, { useState } from "react";
import ExtraHoursService from "../services/extrahours.service"; 

const CalculateExtraHours = () => {
    const [year, setYear] = useState("");
    const [month, setMonth] = useState("");
    const [message, setMessage] = useState("");

    const handleCalculate = async (e) => {
        e.preventDefault();
        try {
            await ExtraHoursService.calculateExtraHours(year, month);
            setMessage("Cálculo de horas extras realizado exitosamente.");
        } catch (error) {
            console.error("Error al calcular las horas extras:", error);
            setMessage("Error al calcular las horas extras.");
        }
    };

    return (
        <div>
            <h2>Calcular Horas Extras</h2>
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

export default CalculateExtraHours;

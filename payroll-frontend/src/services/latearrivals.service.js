import httpClient from "../http-common";

const calculateLateArrivals = (year, month) => {
    return httpClient.post(`/api/v1/latearrivals/calculate/${year}/${month}`);
}

export default { calculateLateArrivals };

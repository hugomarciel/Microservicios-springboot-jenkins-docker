import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/v1/paycheck/');
}

const calculatePaycheck = (year,month) => {
    return httpClient.post(`/api/v1/paycheck/calculate/${year}/${month}`);
}

export default { getAll, calculatePaycheck };
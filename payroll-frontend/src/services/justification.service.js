import httpClient from "../http-common";

const getAll = () => {
  return httpClient.get("/api/v1/justifications/");
}

const create = (data) => {
  return httpClient.post("/api/v1/justifications/", data);
}

const get = (id) => {
  return httpClient.get(`/api/v1/justifications/${id}`);
}

const update = (data) => {
  return httpClient.put("/api/v1/justifications/", data);
}

const remove = (id) => {
  return httpClient.delete(`/api/v1/justifications/${id}`);
}

export default { getAll, create, get, update, remove };


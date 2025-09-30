import httpClient from "../http-common";

const uploadFile = (formData) => {
    return httpClient.post("/api/v1/inandout/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
};

export default { uploadFile };

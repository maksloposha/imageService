

import api from "../utils/api";

export const fetchAllImages = async (setFilteredImages, setError, setLoading) => {
    try {
        setLoading(true);
        const response = await api.get('/images/getAll');
        setFilteredImages(response.data);
    } catch (error) {
        setError("Error fetching images. Please try again later.");
    } finally {
        setLoading(false);
    }
};


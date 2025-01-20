
import { useState } from 'react';
import api from "../utils/api";

const useSearch = () => {
    const [searchQuery, setSearchQuery] = useState("");
    const [filteredImages, setFilteredImages] = useState([]);
    const [error, setError] = useState(null);

    const fetchImagesWithItem = async (imageItem,  setLoadingFilteredImages) => {
        try {
            setLoadingFilteredImages(true)
            const response = await api.get(`/images/getAllWithItem/${imageItem}`);
            setFilteredImages(response.data);
            setError(null);
        } catch (error) {
            setError(`Error fetching images for item: ${imageItem}. Please try again.`);
            console.error("Error fetching filtered images:", error);
        }finally {
            setLoadingFilteredImages(false)
        }
    };

    return {
        searchQuery,
        setSearchQuery,
        filteredImages,
        setFilteredImages,
        error,
        setError,
        fetchImagesWithItem,
    };
};

export default useSearch;

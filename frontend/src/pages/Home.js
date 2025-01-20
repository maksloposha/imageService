import React, { useEffect, useState } from "react";
import useSearch from "../hooks/useSearch";
import ImageUpload from "../components/features/ImageUploader/ImageUpload";
import SearchSection from "../components/features/Search/SearchSection";
import ErrorMessage from "../components/ErrorMessage";
import { fetchAllImages } from "../hooks/fetchImages";
import LoadingImages from "../components/features/imageGallery/LoadingImages";
import ImageGallery from "../components/features/imageGallery/ImageGallery";
import '../components/features/Search/styles.css';
import '../components/ErrorMessage.css';
import './styles.css';

const Home = () => {
    const [loadingAllImages, setLoadingAllImages] = useState(false);
    const [loadingFilteredImages, setLoadingFilteredImages] = useState(false);
    const [allImages, setAllImages] = useState([]);
    const {
        searchQuery,
        setSearchQuery,
        filteredImages,
        setFilteredImages,
        error,
        setError,
        fetchImagesWithItem
    } = useSearch();

    useEffect(() => {
        const loadAllImages = async () => {
            setLoadingAllImages(true);
            await fetchAllImages(setAllImages, setError, setLoadingAllImages);
            setLoadingAllImages(false);
        };

        loadAllImages();
    }, [setAllImages, setError]);

    const handleSearch = () => {
        if (searchQuery) {
            fetchImagesWithItem(searchQuery, setLoadingFilteredImages);
        } else {
            setFilteredImages([]);
            setError(null);
        }
    };

    return (
        <div className="app">
            <h1>Image Search Application</h1>

            <ImageUpload
                setError={setError}
                fetchAllImages={fetchAllImages}
                setLoadingAllImages={setLoadingAllImages}
                setAllImages={setAllImages}
            />

            <SearchSection
                searchQuery={searchQuery}
                setSearchQuery={setSearchQuery}
                handleSearch={handleSearch}
            />

            {error && <ErrorMessage message={error}/>}

            <h2>Images with item</h2>
            {loadingFilteredImages ? (
                <LoadingImages message="Loading filtered images..."/>
            ) : (
                <ImageGallery images={filteredImages} title="Search Results"/>
            )}

            <h2>All Images</h2>
            {loadingAllImages ? (
                <LoadingImages message="Loading all images..."/>
            ) : (
                <ImageGallery images={allImages} title="All Images"/>
            )}
        </div>
    );
};

export default Home;

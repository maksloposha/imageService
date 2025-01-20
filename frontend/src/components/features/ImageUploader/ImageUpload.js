import React, { useState, useRef } from 'react';
import Button from '../../common/Button';
import './styles.css';
import api from "../../../utils/api";

const ImageUpload = ({ setAllImages, setError, fetchAllImages, setLoadingAllImages}) => {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);
    const [error, setErrorState] = useState(null);
    const fileInputRef = useRef(null);

    const handleFileUpload = async () => {
        if (!file) {
            setErrorState("Please select a file to upload.");
            return;
        }

        setUploading(true);
        setErrorState(null);

        const formData = new FormData();
        formData.append("file", file);

        try {
            await api.post("/images/uploadFile", formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });


            await fetchAllImages(setAllImages, setError, setLoadingAllImages);
            setFile(null);
            if (fileInputRef.current) {
                fileInputRef.current.value = "";
            }
        } catch (error) {
            setErrorState("Error uploading file. Please try again.");
            console.error("Error uploading file:", error);
        } finally {
            setUploading(false);
        }
    };

    return (
        <div className="upload-section">
            <label htmlFor="file-upload">Upload Image</label>
            <input
                type="file"
                id="file-upload"
                accept="image/*"
                onChange={(e) => setFile(e.target.files[0])}
                disabled={uploading}
                ref={fileInputRef}
            />

            <Button onClick={handleFileUpload} disabled={uploading}>
                {uploading ? "Uploading..." : "Upload"}
            </Button>

            {uploading && (
                <div className="loading-indicator">
                    <span>Uploading...</span>
                </div>
            )}

            {error && (
                <div className="error-message">
                    <span>{error}</span>
                </div>
            )}
        </div>
    );
};

export default ImageUpload;

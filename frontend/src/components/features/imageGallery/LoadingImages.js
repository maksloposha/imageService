// src/components/LoadingImages.js
import React from 'react';

const LoadingImages = ({ message }) => {
    return (
        <div className="loading-indicator">
            <span>{message}</span>
        </div>
    );
};

export default LoadingImages;

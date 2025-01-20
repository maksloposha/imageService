// src/components/ImageGallery.js
import React from 'react';

const ImageGallery = ({ images, title}) => {
    return (
        <div className={`${title.toLowerCase().replace(" ", "-")}-section`}>
            {images.length > 0 ? (
                <div className="results-section">
                    {images.map((image, index) => (
                        <img key={index} src={image} alt={`${title} ${index}`} />
                    ))}
                </div>
            ) : (
                <p>No images available.</p>
            )}
        </div>
    );
};

export default ImageGallery;

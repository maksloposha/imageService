
import React from 'react';

const SearchSection = ({ searchQuery, setSearchQuery, handleSearch }) => (
    <div className="search-section">
        <p>Please find me images that contain the following items:</p>
        <input
            type="text"
            placeholder="animal"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
    </div>
);

export default SearchSection;


import React from 'react';

const Modal = ({ show, children, closeModal }) => {
    if (!show) return null;

    return (
        <div className="modal">
            <div className="modal-content">
                <button onClick={closeModal} className="close-button">X</button>
                {children}
            </div>
        </div>
    );
};

export default Modal;

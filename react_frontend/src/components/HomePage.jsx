import React, { useState } from 'react';
import axios from 'axios';
import Loader from './Loader';
import { useNavigate } from 'react-router-dom'

const HomePage = () => {
    const [loading, setLoading] = useState(false);
    let name = '';
    const navigate = useNavigate();

    const submitHandler = (e) => {
        e.preventDefault()
        name = e.target.elements.name.value;
        if (!name) {
            alert('Please enter your name')
            return;
        }
        setLoading(true);
        axios.post(`http://localhost:8080/api/generate?userName=${name}`)
            .then(res => {
                localStorage.setItem('userName', name);
                //Navigate to the GamePage  
                console.log(res);
                setLoading(false);
                navigate('/game')
            }).catch(err => {
                setLoading(false)
            })
    }

    return (
        <div className='container'>
            <div className='card'>
                <div className='card-header'>
                    <h1 className='text-center'>Welcome to the Guessing Game!</h1>
                </div>
                <div className='card-body'>
                    <form onSubmit={submitHandler}>
                        <div className='form-group'>
                            <label htmlFor='name'>Enter your name:</label>
                            <input type='text' className='form-control ' id='name' />
                        </div>
                        <div className='text-center pt-4'>
                            {loading ? <Loader /> : <button type='submit' className='btn btn-primary'>Start Game</button>
                            }
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default HomePage
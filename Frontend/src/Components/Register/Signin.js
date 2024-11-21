import { Button } from '@mui/material'
import { green } from '@mui/material/colors';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material//Alert';
import { useDispatch } from 'react-redux';
import { login } from '../../Redux/Auth/Action';
import { useEffect } from 'react';
import { currentUser } from '../../Redux/Auth/Action';
import { useSelector } from 'react-redux';

const Signin = () => {

    const [inputData, setInputData] = useState({ email: "", password: "" });

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { auth } = useSelector(store => store);   
    const token = localStorage.getItem("token");

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("handle submit");
        setOpenSnackBar(true);
        dispatch(login(inputData));
        navigate("/");

    }

    const handleChange = (e) => {
        setInputData({
            ...inputData,
            [e.target.name]: e.target.value,
        });
    }

    const [openSnackBar, setOpenSnackBar] = useState(false);

    const handleSnackBarClose = () => {
        setOpenSnackBar(false);
    }


    useEffect(() => {

        if (token) {
            dispatch(currentUser(token));
        }

    }, [token]);

    useEffect(() => {
        if (auth?.reqUser?.fullname) {
            navigate("/");
        }
    }, [auth.reqUser]);


    return (
        <div>
            <div className=' flex justify-center h-screen items-center'>

                <div className=' w-[30%] p-10 shadow-md bg-white rounded-lg'>

                    <form onSubmit={handleSubmit} className=' space-y-5'>

                        <div>
                            <p className=' mb-2 font-bold'>Email</p>
                            <input
                                placeholder=' Enter your Email'
                                value={inputData.email}
                                name='email'
                                onChange={handleChange}
                                type='text' className=' py-2 border-green-600 w-full rounded-md border-4' />
                        </div>

                        <div>
                            <p className=' mb-2 font-bold'>Password</p>
                            <input
                                placeholder=' Enter your Password'
                                value={inputData.password}
                                name='password'
                                onChange={handleChange}
                                type='text' className=' py-2  w-full rounded-md border-green-600 border-4' />
                        </div>

                        <div>
                            <Button type='submit' sx={{ bgcolor: green[700], padding: "0.5rem 0rem" }} className=' w-full' variant='contained'>Sign In</Button>
                        </div>

                    </form>

                    <div className=' flex space-x-3 items-center mt-5'>
                        <p className=' m-0 font-semibold'>  Create New Account </p>
                        <Button style={{ fontWeight: 'bold' }} variant='text' onClick={() => navigate("/signup")}>signup</Button>
                    </div>

                </div>

            </div>

            <Snackbar open={openSnackBar} autoHideDuration={6000} onClose={handleSnackBarClose}>
                <Alert
                    onClose={handleSnackBarClose}
                    severity="success"
                    variant="standard"
                    sx={{ width: '100%' }}
                >
                    This is a success Alert inside a Snackbar!
                </Alert>
            </Snackbar>

        </div>
    )
}

export default Signin

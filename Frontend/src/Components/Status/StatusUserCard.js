import React from 'react'
import { useNavigate } from 'react-router-dom'

const StatusUserCard = () => {

    const navigate = useNavigate();

    const handleNavigate = () =>{
        navigate(`/status/{userId}`)
    }

    return (
        <div onClick={handleNavigate} className=' flex items-center p-3'>
            <div>
                <img
                    className=' h-7 w-7 lg:w-10 lg:h-10 rounded-full'
                    src='https://cdn.pixabay.com/photo/2023/08/02/02/46/woman-8164186_960_720.jpg'
                    alt=''
                />
            </div>

            <div className=' text-white ml-2'>
                <p>Faiak Zindabad</p>
            </div>

        </div>
    )
}

export default StatusUserCard

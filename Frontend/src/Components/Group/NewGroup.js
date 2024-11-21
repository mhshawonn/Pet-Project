import { Button, CircularProgress } from '@mui/material'
import React from 'react'
import { useState } from 'react'
import { BsArrowLeft } from 'react-icons/bs'
import { BsCheck2 } from 'react-icons/bs'

const NewGroup = () => {

    const [isImageUploading, setIsImageUploading] = useState(false);
    const [groupName, setGroupName] = useState('');

    return (
        <div className=' w-full h-full'>
            <div className=' flex items-center space-x-10 bg-green-400 text-white pt-16 px-10 pb-5'>
                <BsArrowLeft
                    className=' cursor-pointer text-2xl font-bold'
                />
                <p className=' text-xl font-semibold '>New Group</p>
            </div>



            <div className=' flex flex-col justify-center items-center my-12' >

                <label
                    htmlFor='ImgInput'
                    className='relative'
                >
                    <img
                        className=' w-44 h-44 rounded-full'
                        src='https://cdn.pixabay.com/photo/2022/08/22/12/42/bird-7403593_960_720.jpg'
                        alt=''
                    />

                    {isImageUploading && <CircularProgress className=' absolute top-[5rem] left-[6rem]' />}
                </label>

                <input
                    type='file'
                    id='ImgInput'
                    className=' hidden'
                    onChange={() => console.log('uploading image')}
                    value={""}
                />
            </div>

            <div className=' w-full justify-between items-center py-2 px-5'>
                <input
                    type='text'
                    placeholder='Group Subject'
                    value={groupName}
                    className=' w-full outline-none border-b-2 border-green-700 px-2 bg-transparent'
                    onChange={(e) => setGroupName(e.target.value)}
                />
            </div>

            {groupName &&
                <div>
                    <Button className=' w-full py-10 bg-slate-200 flex  items-center justify-center'>
                        <div className=' bg-[#0c977d] rounded-full p-4'>
                            <BsCheck2 className=' text-white font-bold text-3xl' />
                        </div>
                    </Button>
                </div>}
        </div>
    )
}

export default NewGroup

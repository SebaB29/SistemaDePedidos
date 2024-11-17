import { useEffect, useState } from 'react'
import { EditAccountForm } from '../components/EditAccountForm'
import Header from '../components/Header'
import { Main } from '../components/Main'
import Modal from '../components/Modal'
import { useModal } from '../hooks/useModal'
import { helpHttp } from '../helpers/helpHttp'
import Loader from '../components/Loader'

export const Account = () => {
  const [isOpenModal, openModal, closeModal] = useModal(false)
  const [loading, setLoading] = useState(false)
  const [response, setResponse] = useState({})
  const ENDPOINT = `http://localhost:8080/user/${window.sessionStorage.getItem('email')}`

  useEffect(() => {
    setLoading(true)
    helpHttp().get(
      ENDPOINT,
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        setLoading(false)
        setResponse(res)
      })
      .catch(err => {
        setLoading(false)
        setResponse({
          status: '500',
          error: err,
          data: null
        })
      })
  }, [])

  return (
    <>
      <Header title='Mi cuenta' />
      <Main>
        {loading && <Loader />}
        {response.status === 'OK' &&
          <><img src={response.data.photo} alt='foto' className='user-image' />
            <ul>
              <li>Nombre: {response.data.username}</li>
              <li>Apellido: {response.data.lastName}</li>
              <li>Email: {response.data.email}</li>
              <li>Edad: {response.data.age}</li>
              <li>Género: {response.data.gender}</li>
              <li>Dirección: {response.data.address}</li>
            </ul>
            <button onClick={openModal}>Editar Perfil</button>
            <Modal isOpen={isOpenModal} closeModal={closeModal}>
              <h3>Editar Perfil</h3>
              <EditAccountForm data={response.data} />
            </Modal>
          </>}

      </Main>
    </>
  )
}

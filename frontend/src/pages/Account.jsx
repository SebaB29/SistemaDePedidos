import { EditAccountForm } from '../components/EditAccountForm'
import Header from '../components/Header'
import { Main } from '../components/Main'
import Modal from '../components/Modal'
import { useModal } from '../hooks/useModal'

export const Account = () => {
  const [isOpenModal, openModal, closeModal] = useModal(false)

  return (
    <>
      <Header title='Mi cuenta' />
      <Main>
        <img src={window.sessionStorage.getItem('photo')} alt='foto' />
        <ul>
          <li>Nombre: {window.sessionStorage.getItem('name')}</li>
          <li>Apellido: {window.sessionStorage.getItem('last_name')}</li>
          <li>Email: {window.sessionStorage.getItem('email')}</li>
          <li>Edad: {window.sessionStorage.getItem('age')}</li>
          <li>Contraseña: {window.sessionStorage.getItem('password')}</li>
          <li>Género: {window.sessionStorage.getItem('gender')}</li>
          <li>Dirección: {window.sessionStorage.getItem('address')}</li>
        </ul>
        <button onClick={openModal}>Editar Perfil</button>
        <Modal isOpen={isOpenModal} closeModal={closeModal}>
          <h3>Editar Perfil</h3>
          <EditAccountForm />
        </Modal>
      </Main>
    </>
  )
}

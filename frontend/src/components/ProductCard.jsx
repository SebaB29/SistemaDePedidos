import { helpHttp } from '../helpers/helpHttp'
import Modal from './Modal'
import { useModal } from '../hooks/useModal'
import { EditProductForm } from './EditProductForm'
import { EditProductStockForm } from './EditProductStockForm'

export const ProductCard = ({ product, setCarrito, itemsCarrito }) => {
  const [isOpenEditProductModal, openEditProductModal, closeEditProductModal] = useModal(false)
  const [isOpenEditStockModal, openEditStockModal, closeEditStockModal] = useModal(false)

  const handleBuy = (e) => {
    let quantity
    while (!/^(0|[1-9][0-9]*)$/.test(quantity)) {
      quantity = window.prompt('Cuantos quieres comprar?')
      if (quantity === null) return
    }
    setCarrito([...itemsCarrito, { productId: product.productId, quantity, name: product.name }])
    e.target.style.display = 'none'
  }

  const handleDelete = () => {
    helpHttp().del(
      `http://localhost:8080/product/${product.productId}`,
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        if (res.status !== 'OK') {
          window.alert(res.error)
        } else {
          console.log(res)
          window.alert(res.data.message)
          window.location.reload()
        }
      })
  }

  return (
    <>
      <article className='product-card'>
        <h4>{product.name}</h4>
        <p>{product.quantity} {product.stockType}</p>
        <button onClick={handleBuy}>Agregar al carrito</button>
        {window.sessionStorage.getItem('rol') === 'ADMIN' &&
          <>
            <button onClick={openEditProductModal}>Editar Producto</button>
            <button onClick={handleDelete}>Eliminar</button>
            <button onClick={openEditStockModal}>Editar Stock</button>
          </>}
      </article>
      <ul className='porperties-list'>
        {product.attributes.map((attribute, index) => (
          <li key={index}>{attribute.description} : {attribute.value}</li>
        ))}
      </ul>

      <Modal isOpen={isOpenEditProductModal} closeModal={closeEditProductModal}>
        <h3>Editar {product.name}</h3>
        <EditProductForm product={product} />
      </Modal>
      <Modal isOpen={isOpenEditStockModal} closeModal={closeEditStockModal}>
        <h3>Stock de {product.name}</h3>
        <EditProductStockForm product={product} />
      </Modal>
    </>
  )
}

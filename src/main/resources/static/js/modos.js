document.addEventListener('DOMContentLoaded', function () {
const temaGuardado = localStorage.getItem('tema');
temaGuardado === 'claro' ? temaClaro() : temaOscuro();
});


const temaOscuro = () => {
    const oscuro =  document.querySelector('body').setAttribute('data-bs-theme', 'dark');
    if(oscuro === 'dark'){
        cambiarIcono();
    }
    
}

const temaClaro = () => {
    const claro = document.querySelector('body').setAttribute('data-bs-theme', 'light');
    if(claro === 'light'){  
        cambiarIcono();
    }
}

const cambiarTema = () => {
    const cambio_del_tema = document.querySelector('body').getAttribute('data-bs-theme');
    if (cambio_del_tema === 'light') {
        temaOscuro();
        localStorage.setItem('tema', 'oscuro'); //!el localStorage sirve para guardar cosas sin que se pierdan durante la página
    } else {
        temaClaro();
        localStorage.setItem('tema','claro'); //!el localStorage sirve para guardar cosas sin que se pierdan durante  la página
    }
}

const cambiarIcono = () => {
    const icono = document.getElementById('icono_tema');

    if (icono.classList.contains('fa-moon')) { /* Si icono contiene la luna remueve luna y cambia a sol */
        icono.classList.remove('fa-moon');
        icono.classList.add('fa-sun');
    } else {
        icono.classList.remove('fa-sun'); /* Si icono contiene sol remueve sol y cambia a luna */
        icono.classList.add('fa-moon');
    }
};


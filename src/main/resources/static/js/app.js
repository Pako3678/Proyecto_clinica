const API_BASE_URL = "http://localhost:8080/api";

let pacientesLista = [];
let pacienteEliminarId = null;
let pacienteActualFicha = null;

const vistaDirectorio = document.getElementById("vistaDirectorio");
const vistaFicha = document.getElementById("vistaFicha");
const tabDirectorio = document.getElementById("tab-directorio");
const tabFicha = document.getElementById("tab-ficha");

const tablaPacientesBody = document.getElementById("tablaPacientesBody");
const cargandoTabla = document.getElementById("cargandoTabla");
const sinPacientes = document.getElementById("sinPacientes");
const filtroTabla = document.getElementById("filtroTabla");
const btnLimpiarFiltro = document.getElementById("btnLimpiarFiltro");
const badgeTotalPacientes = document.getElementById("badgeTotalPacientes");

const formBusquedaDni = document.getElementById("formBusquedaDni");
const txtDni = document.getElementById("txtDni");
const cargandoFicha = document.getElementById("cargandoFicha");
const alertaErrorFicha = document.getElementById("alertaErrorFicha");
const mensajeErrorFicha = document.getElementById("mensajeErrorFicha");
const resultadoFicha = document.getElementById("resultadoFicha");
const quickDniPills = document.getElementById("quickDniPills");

const btnEditarDesdeFicha = document.getElementById("btnEditarDesdeFicha");
const btnEliminarDesdeFicha = document.getElementById("btnEliminarDesdeFicha");

const modalPacienteEl = document.getElementById("modalPaciente");
const formPaciente = document.getElementById("formPaciente");
const modalPacienteLabel = document.getElementById("modalPacienteLabel");
const modalPacienteSubtitulo = document.getElementById("modalPacienteSubtitulo");
const modalErrorBanner = document.getElementById("modalErrorBanner");
const modalErrorTexto = document.getElementById("modalErrorTexto");
const btnGuardarPaciente = document.getElementById("btnGuardarPaciente");
const btnGuardarTexto = document.getElementById("btnGuardarTexto");
const spinnerGuardar = document.getElementById("spinnerGuardar");

const pacienteIdInput = document.getElementById("pacienteId");
const inputNombre = document.getElementById("inputNombre");
const inputApellido = document.getElementById("inputApellido");
const inputDni = document.getElementById("inputDni");
const inputTelefono = document.getElementById("inputTelefono");
const inputObraSocial = document.getElementById("inputObraSocial");
const inputNumeroAfiliado = document.getElementById("inputNumeroAfiliado");
const inputAlergias = document.getElementById("inputAlergias");

const modalEliminarEl = document.getElementById("modalEliminar");
const deleteNombrePaciente = document.getElementById("deleteNombrePaciente");
const deleteDniPaciente = document.getElementById("deleteDniPaciente");
const btnConfirmarEliminar = document.getElementById("btnConfirmarEliminar");
const spinnerEliminar = document.getElementById("spinnerEliminar");

const toastFeedbackEl = document.getElementById("toastFeedback");
const toastMensaje = document.getElementById("toastMensaje");

let modalPaciente = null;
let modalEliminar = null;
let toastFeedback = null;

document.addEventListener("DOMContentLoaded", () => {
    modalPaciente = new bootstrap.Modal(modalPacienteEl);
    modalEliminar = new bootstrap.Modal(modalEliminarEl);
    toastFeedback = new bootstrap.Toast(toastFeedbackEl, { delay: 3500 });

    txtDni.addEventListener("input", () => {
        txtDni.value = txtDni.value.replace(/\D/g, "").slice(0, 8);
    });

    inputDni.addEventListener("input", () => {
        inputDni.value = inputDni.value.replace(/\D/g, "").slice(0, 8);
    });

    inputTelefono.addEventListener("input", () => {
        inputTelefono.value = inputTelefono.value.replace(/\D/g, "").slice(0, 15);
    });

    inputNumeroAfiliado.addEventListener("input", () => {
        inputNumeroAfiliado.value = inputNumeroAfiliado.value.replace(/\D/g, "").slice(0, 15);
    });

    filtroTabla.addEventListener("input", () => {
        const query = filtroTabla.value.trim().toLowerCase();
        filtrarTablaLocal(query);
    });

    btnLimpiarFiltro.addEventListener("click", () => {
        filtroTabla.value = "";
        renderizarTabla(pacientesLista);
    });

    formBusquedaDni.addEventListener("submit", (e) => {
        e.preventDefault();
        const dni = txtDni.value.trim();
        if (dni) {
            consultarFichaPorDni(dni);
        }
    });

    formPaciente.addEventListener("submit", guardarPaciente);

    btnConfirmarEliminar.addEventListener("click", ejecutarEliminacion);

    cargarPacientes();
});

function cambiarPestana(pestana) {
    if (pestana === "directorio") {
        tabDirectorio.classList.add("active");
        tabFicha.classList.remove("active");
        vistaDirectorio.classList.remove("d-none");
        vistaFicha.classList.add("d-none");
    } else if (pestana === "ficha") {
        tabFicha.classList.add("active");
        tabDirectorio.classList.remove("active");
        vistaFicha.classList.remove("d-none");
        vistaDirectorio.classList.add("d-none");
    }
}

async function cargarPacientes() {
    cargandoTabla.classList.remove("d-none");
    sinPacientes.classList.add("d-none");
    tablaPacientesBody.innerHTML = "";

    try {
        const res = await fetch(`${API_BASE_URL}/pacientes`);
        if (!res.ok) {
            throw new Error("Error al consultar el servidor");
        }

        pacientesLista = await res.json();
        badgeTotalPacientes.textContent = pacientesLista.length;
        renderizarTabla(pacientesLista);
        generarPildorasRapidas(pacientesLista);

    } catch (error) {
        console.error("Error al cargar pacientes:", error);
        mostrarToast("No se pudo conectar con el servidor. Verifique que el backend esté corriendo.", "danger");
        sinPacientes.classList.remove("d-none");
    } finally {
        cargandoTabla.classList.add("d-none");
    }
}

function filtrarTablaLocal(query) {
    if (!query) {
        renderizarTabla(pacientesLista);
        return;
    }

    const filtrados = pacientesLista.filter(p => {
        const nombreCompleto = `${p.nombre} ${p.apellido}`.toLowerCase();
        const dniStr = String(p.dni);
        const obraSocialStr = (p.obraSocial || "").toLowerCase();
        return nombreCompleto.includes(query) || dniStr.includes(query) || obraSocialStr.includes(query);
    });

    renderizarTabla(filtrados);
}

function renderizarTabla(pacientes) {
    tablaPacientesBody.innerHTML = "";

    if (!pacientes || pacientes.length === 0) {
        sinPacientes.classList.remove("d-none");
        return;
    }

    sinPacientes.classList.add("d-none");

    pacientes.forEach(p => {
        const tr = document.createElement("tr");

        const iniciales = `${(p.nombre || "").charAt(0)}${(p.apellido || "").charAt(0)}`.toUpperCase() || "PA";

        let alergiasBadge = `<span class="badge badge-alergia-no"><i class="bi bi-check2 me-1"></i>Sin registrar</span>`;
        if (p.alergias && p.alergias.trim() !== "" && !p.alergias.toLowerCase().includes("sin alergias")) {
            alergiasBadge = `<span class="badge badge-alergia-si" title="${p.alergias}"><i class="bi bi-exclamation-circle-fill me-1"></i>${p.alergias}</span>`;
        }

        const telStr = p.telefono ? `<i class="bi bi-telephone me-1 text-muted"></i>${p.telefono}` : `<span class="text-muted fst-italic">No registrado</span>`;

        const osStr = p.obraSocial ? `<span class="badge badge-os">${p.obraSocial}</span> ${p.numeroAfiliado ? `<small class="text-muted d-block">Af: ${p.numeroAfiliado}</small>` : ''}` : `<span class="text-muted fst-italic">Particular</span>`;

        tr.innerHTML = `
            <td class="ps-4">
                <div class="d-flex align-items-center gap-3">
                    <div class="avatar-circle">${iniciales}</div>
                    <div>
                        <strong class="d-block text-slate-800">${p.nombre} ${p.apellido}</strong>
                        <small class="text-muted">ID Sistema: #${p.id}</small>
                    </div>
                </div>
            </td>
            <td>
                <span class="fw-semibold text-slate-700">${p.dni}</span>
            </td>
            <td>${telStr}</td>
            <td>${osStr}</td>
            <td>${alergiasBadge}</td>
            <td class="text-end pe-4">
                <div class="btn-group" role="group">
                    <button class="btn btn-sm btn-outline-teal btn-action-icon me-1" title="Ver Ficha Médica" onclick="irAFicha(${p.dni})">
                        <i class="bi bi-file-earmark-medical"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-primary btn-action-icon me-1" title="Editar Paciente" onclick="abrirModalEditar(${p.id})">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger btn-action-icon" title="Eliminar Paciente" onclick="abrirModalEliminar(${p.id}, '${p.nombre} ${p.apellido}', ${p.dni})">
                        <i class="bi bi-trash3"></i>
                    </button>
                </div>
            </td>
        `;

        tablaPacientesBody.appendChild(tr);
    });
}

function generarPildorasRapidas(pacientes) {
    quickDniPills.innerHTML = `<span class="text-muted small">Sugerencias rápidas:</span>`;
    const muestra = pacientes.slice(0, 4);
    muestra.forEach(p => {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "quick-dni-btn";
        btn.textContent = `${p.nombre} ${p.apellido} (${p.dni})`;
        btn.onclick = () => {
            txtDni.value = p.dni;
            consultarFichaPorDni(p.dni);
        };
        quickDniPills.appendChild(btn);
    });
}

function abrirModalNuevo() {
    formPaciente.reset();
    formPaciente.classList.remove("was-validated");
    pacienteIdInput.value = "";
    modalPacienteLabel.textContent = "Nuevo Paciente";
    modalPacienteSubtitulo.textContent = "Complete los datos para dar de alta al paciente en el sistema.";
    btnGuardarTexto.textContent = "Registrar Paciente";
    ocultarErrorModal();
    modalPaciente.show();
}

async function abrirModalEditar(id) {
    formPaciente.reset();
    formPaciente.classList.remove("was-validated");
    ocultarErrorModal();

    btnGuardarTexto.textContent = "Actualizando...";
    btnGuardarPaciente.disabled = true;

    try {
        const res = await fetch(`${API_BASE_URL}/pacientes/${id}`);
        if (!res.ok) {
            throw new Error("No se pudo obtener la información del paciente");
        }

        const p = await res.json();
        pacienteIdInput.value = p.id;
        inputNombre.value = p.nombre || "";
        inputApellido.value = p.apellido || "";
        inputDni.value = p.dni || "";
        inputTelefono.value = p.telefono || "";
        inputObraSocial.value = p.obraSocial || "";
        inputNumeroAfiliado.value = p.numeroAfiliado || "";
        inputAlergias.value = p.alergias || "";

        modalPacienteLabel.textContent = `Editar Paciente #${p.id}`;
        modalPacienteSubtitulo.textContent = `Modificando datos de ${p.nombre} ${p.apellido}`;
        btnGuardarTexto.textContent = "Guardar Cambios";
        btnGuardarPaciente.disabled = false;

        modalPaciente.show();

    } catch (error) {
        console.error(error);
        mostrarToast("Error al cargar los datos del paciente", "danger");
        btnGuardarPaciente.disabled = false;
        btnGuardarTexto.textContent = "Guardar";
    }
}

async function guardarPaciente(event) {
    event.preventDefault();

    if (!formPaciente.checkValidity()) {
        formPaciente.classList.add("was-validated");
        return;
    }

    const id = pacienteIdInput.value;
    const esEdicion = Boolean(id);

    const payload = {
        nombre: inputNombre.value.trim(),
        apellido: inputApellido.value.trim(),
        dni: parseInt(inputDni.value.trim(), 10),
        telefono: inputTelefono.value.trim() ? parseInt(inputTelefono.value.trim(), 10) : null,
        obraSocial: inputObraSocial.value.trim() || null,
        numeroAfiliado: inputNumeroAfiliado.value.trim() ? parseInt(inputNumeroAfiliado.value.trim(), 10) : null,
        alergias: inputAlergias.value.trim() || null
    };

    if (isNaN(payload.dni) || payload.dni < 1000000 || payload.dni > 99999999) {
        mostrarErrorModal("El DNI debe tener entre 7 y 8 números válidos.");
        return;
    }

    setEstadoGuardando(true);
    ocultarErrorModal();

    try {
        const url = esEdicion ? `${API_BASE_URL}/pacientes/${id}` : `${API_BASE_URL}/pacientes`;
        const metodo = esEdicion ? "PUT" : "POST";

        const res = await fetch(url, {
            method: metodo,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        const data = await res.json();

        if (!res.ok) {
            const mensaje = data.error || "Ocurrió un error al guardar los datos del paciente.";
            mostrarErrorModal(mensaje);
            return;
        }

        modalPaciente.hide();
        mostrarToast(esEdicion ? "Paciente actualizado exitosamente." : "Paciente registrado exitosamente.", "success");

        await cargarPacientes();

        if (pacienteActualFicha && String(pacienteActualFicha) === String(payload.dni)) {
            consultarFichaPorDni(payload.dni);
        }

    } catch (error) {
        console.error("Error al guardar paciente:", error);
        mostrarErrorModal("No se pudo conectar con el servidor para guardar los cambios.");
    } finally {
        setEstadoGuardando(false);
    }
}

function setEstadoGuardando(cargando) {
    btnGuardarPaciente.disabled = cargando;
    if (cargando) {
        spinnerGuardar.classList.remove("d-none");
    } else {
        spinnerGuardar.classList.add("d-none");
    }
}

function mostrarErrorModal(mensaje) {
    modalErrorTexto.textContent = mensaje;
    modalErrorBanner.classList.remove("d-none");
}

function ocultarErrorModal() {
    modalErrorBanner.classList.add("d-none");
}

function abrirModalEliminar(id, nombreCompleto, dni) {
    pacienteEliminarId = id;
    deleteNombrePaciente.textContent = nombreCompleto;
    deleteDniPaciente.textContent = dni;
    modalEliminar.show();
}

async function ejecutarEliminacion() {
    if (!pacienteEliminarId) return;

    btnConfirmarEliminar.disabled = true;
    spinnerEliminar.classList.remove("d-none");

    try {
        const res = await fetch(`${API_BASE_URL}/pacientes/${pacienteEliminarId}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            throw new Error("No se pudo eliminar el paciente.");
        }

        modalEliminar.hide();
        mostrarToast("Paciente e historia clínica eliminados correctamente.", "success");

        const pacienteEliminado = pacientesLista.find(p => p.id === pacienteEliminarId);
        if (pacienteEliminado && pacienteActualFicha === pacienteEliminado.dni) {
            resultadoFicha.classList.add("d-none");
            pacienteActualFicha = null;
        }

        await cargarPacientes();

    } catch (error) {
        console.error("Error al eliminar:", error);
        mostrarToast("Ocurrió un error al intentar eliminar el paciente.", "danger");
    } finally {
        btnConfirmarEliminar.disabled = false;
        spinnerEliminar.classList.add("d-none");
        pacienteEliminarId = null;
    }
}

function irAFicha(dni) {
    cambiarPestana("ficha");
    txtDni.value = dni;
    consultarFichaPorDni(dni);
}

async function consultarFichaPorDni(dni) {
    ocultarMensajesFicha();
    cargandoFicha.classList.remove("d-none");

    try {
        const res = await fetch(`${API_BASE_URL}/pacientes/buscar/${dni}`);

        if (res.status === 404) {
            mostrarErrorFicha("No se encontró ningún paciente con el DNI " + dni);
            return;
        }

        if (!res.ok) {
            mostrarErrorFicha("Ocurrió un error al consultar el servidor.");
            return;
        }

        const data = await res.json();
        pacienteActualFicha = data.dni;
        mostrarResultadoFicha(data);

    } catch (error) {
        console.error("Error al consultar ficha:", error);
        mostrarErrorFicha("No se pudo conectar con el servidor. ¿Está corriendo en " + API_BASE_URL + "?");
    } finally {
        cargandoFicha.classList.add("d-none");
    }
}

function mostrarResultadoFicha(p) {
    const iniciales = `${(p.nombre || "").charAt(0)}${(p.apellido || "").charAt(0)}`.toUpperCase() || "PA";
    document.getElementById("avatarFicha").textContent = iniciales;

    document.getElementById("badgeIdReporte").textContent = p.idReporte ? `REP-${String(p.idReporte).padStart(5, "0")}` : "REP-NUEVO";
    document.getElementById("valNombreCompleto").textContent = `${p.nombre} ${p.apellido}`;
    document.getElementById("valDniFicha").textContent = p.dni ?? "-";
    document.getElementById("valNombre").textContent = p.nombre ?? "-";
    document.getElementById("valApellido").textContent = p.apellido ?? "-";
    document.getElementById("valObraSocial").textContent = p.obraSocial || "Particular";
    document.getElementById("valNumeroAfiliado").textContent = p.numeroAfiliado ?? "N/A";
    document.getElementById("valAlergias").textContent = p.alergias || "Sin alergias registradas";

    const pacienteEnLista = pacientesLista.find(item => item.dni === p.dni);
    if (pacienteEnLista) {
        btnEditarDesdeFicha.onclick = () => abrirModalEditar(pacienteEnLista.id);
        btnEliminarDesdeFicha.onclick = () => abrirModalEliminar(pacienteEnLista.id, `${p.nombre} ${p.apellido}`, p.dni);
        btnEditarDesdeFicha.disabled = false;
        btnEliminarDesdeFicha.disabled = false;
    } else {
        btnEditarDesdeFicha.disabled = true;
        btnEliminarDesdeFicha.disabled = true;
    }

    llenarLista("listaMedicamentos", p.medicamentosRecetados, "bi-capsule", "Sin medicamentos registrados");
    llenarLista("listaEstudios", p.estudiosRealizados, "bi-clipboard2-pulse", "Sin estudios registrados");
    llenarLista("listaMedicos", p.medicosVisitados, "bi-person-badge", "Sin consultas médicas previas");

    resultadoFicha.classList.remove("d-none");
}

function llenarLista(idLista, items, icono, mensajeVacio) {
    const ul = document.getElementById(idLista);
    ul.innerHTML = "";

    if (!items || items.length === 0) {
        const li = document.createElement("li");
        li.className = "list-group-item text-muted fst-italic py-3 text-center";
        li.textContent = mensajeVacio;
        ul.appendChild(li);
        return;
    }

    items.forEach(item => {
        const li = document.createElement("li");
        li.className = "list-group-item d-flex align-items-center gap-2";
        li.innerHTML = `<i class="bi ${icono} text-teal small"></i> <span>${item}</span>`;
        ul.appendChild(li);
    });
}

function mostrarErrorFicha(mensaje) {
    ocultarMensajesFicha();
    mensajeErrorFicha.textContent = mensaje;
    alertaErrorFicha.classList.remove("d-none");
}

function ocultarMensajesFicha() {
    alertaErrorFicha.classList.add("d-none");
    resultadoFicha.classList.add("d-none");
}

function mostrarToast(mensaje, tipo = "success") {
    const icono = tipo === "success" 
        ? '<i class="bi bi-check-circle-fill text-success fs-5"></i>' 
        : '<i class="bi bi-exclamation-triangle-fill text-danger fs-5"></i>';

    toastMensaje.innerHTML = `${icono} <span>${mensaje}</span>`;
    toastFeedback.show();
}

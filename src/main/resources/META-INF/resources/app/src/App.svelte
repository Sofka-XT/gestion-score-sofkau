<script>
	const BASE_URL = "http://localhost:8080/api"
	let courses = [];
	let programs = [{ id: 1, name: `C4` }, { id: 2, name: `C5` }];

	let prefix = "";
	let courseId = "";
	let name = "";
	let program = {};
	let categories = "";
	let i = 0;

	$: filteredCourses = prefix
		? courses.filter((course) => {
				const name = `${course.name}, ${course.courseId}`;
				return name.toLowerCase().startsWith(prefix.toLowerCase());
		  })
		: courses;

	$: selected = filteredCourses[i];

	$: reset_inputs(selected);

	function create() {
		const model = { programId: program.id, courseId, name, categories };
		console.log(model);
		fetch(BASE_URL+"/addCourse", {
			method: 'POST', 
			mode: 'cors', 
			cache: 'no-cache', 
			headers: {
			'Content-Type': 'application/json'
			},
			body: JSON.stringify(model) 
		})
		.then(response => response.json())
		.then(() => {
			subscribe(program.id, (event) => {
				console.log(event);
				courses = courses.concat(model);
				i = courses.length - 1;
				clear();
			}, (error) => {
				console.log(error);
			});
		});
		
	}

	function subscribe(aggregateId, resolve, reject){
		const socket = new WebSocket("ws://" + location.host + "/retrieve/" + aggregateId);
        socket.onopen = function() {
            console.log("Connected to the web socket");
         };
        socket.onmessage =function(m) {
            console.log("Got message: " + m.data);
			resolve(m.data);
			socket.close();
        };

		socket.onerror =function(m) {
			reject(m);
			socket.close();
        };
	}

	function update() {
		selected.name = name;
		selected.courseId = courseId;
		selected.program = program;
		selected.categories = categories;
		courses = courses;
	}

	function remove() {
		const index = courses.indexOf(selected);
		courses = [...courses.slice(0, index), ...courses.slice(index + 1)];
		i = Math.min(i, filteredCourses.length - 2);
		clear();
	}

	function reset_inputs(course) {
		name = course ? course.name : "";
		courseId = course ? course.courseId : "";
		program = course ? course.program : {};
		categories = course ? course.categories : "";
	}

	function clear() {
		name = "";
		courseId = "";
		program = {};
	}
</script>

<h2>GESTIÓN DE CURSOS</h2>
<i>Programa: </i>
<label>
	<select bind:value={program} >
		{#each programs as program}
			<option value={program.id}>
				{program.name}
			</option>
		{/each}
	</select>
</label>
<i>Curso: </i>
<label><input bind:value={courseId} placeholder="Identificador" /></label>
<label><input bind:value={name} placeholder="Nombre" /></label>
<i>Categorias: </i>
<label><textarea bind:value={categories} placeholder="Categorias separados por comas" /></label>
<div class="buttons">
	<button on:click={create} disabled={!courseId || !name || !categories}>Crear</button>
	<button on:click={update} disabled={!courseId || !name || !!categories || !selected}>Editar</button>
</div>
<input placeholder="Buscar" bind:value={prefix} />
<select bind:value={i} size={6}>
	{#each filteredCourses as course, i}
		<option value={i}>{course.courseId}, {course.name}</option>
	{/each}
</select>
<div class="buttons">
	<button on:click={remove} disabled={!selected}>Eliminar</button>
</div>

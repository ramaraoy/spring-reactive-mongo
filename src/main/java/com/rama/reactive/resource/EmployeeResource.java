package com.rama.reactive.resource;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rama.reactive.model.Employee;
import com.rama.reactive.model.EmployeeEvent;
import com.rama.reactive.repo.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@GetMapping("/all")
	public Flux<Employee> getAll() {		
		return employeeRepo.findAll();		
	}

	@GetMapping("/{id}")
	public Mono<Employee> findById(@PathVariable("id") final String id) {		
		return employeeRepo.findById(id);		
	}

	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable("id")
                                    final String empId) {
        return employeeRepo.findById(empId)
                .flatMapMany(employee -> {

                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

                    Flux<EmployeeEvent> employeeEventFlux =
                            Flux.fromStream(
                                    Stream.generate(() -> new EmployeeEvent(employee,
                                            new Date()))
                            );


                    return Flux.zip(interval, employeeEventFlux)
                            .map(Tuple2::getT2);

                });

    }


	
}

package com.example.bnyan.Controller;

import com.example.bnyan.Api.ApiResponse;
import com.example.bnyan.Model.User;
import com.example.bnyan.Model.UserRequest;
import com.example.bnyan.Service.UserRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user-request")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(userRequestService.get());
    }

    @PostMapping("/add/{builtId}")
    public ResponseEntity<?> add(
            @AuthenticationPrincipal User user,
            @PathVariable Integer builtId,
            @RequestBody @Valid UserRequest userRequest
    ) {
        userRequestService.add(user.getId(), builtId, userRequest);
        return ResponseEntity.ok(new ApiResponse("User request added"));
    }


    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Integer requestId
    ) {
        userRequestService.delete(requestId, user.getId());
        return ResponseEntity.ok(new ApiResponse("User request deleted"));
    }


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getUserRequestById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(userRequestService.getUserRequestById(id));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getUserRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body(userRequestService.getUserRequestsByStatus(status));
    }

    @GetMapping("/get-by-type/{type}")
    public ResponseEntity<?> getUserRequestsByType(@PathVariable String type) {
        return ResponseEntity.status(200).body(userRequestService.getUserRequestsByType(type));
    }

    @GetMapping("/get-by-customer-id/{customerId}")
    public ResponseEntity<?> getUserRequestsByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(userRequestService.getUserRequestsByCustomerId(customerId));
    }

    @PutMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(
            @AuthenticationPrincipal User user,
            @PathVariable Integer requestId
    ) {
        userRequestService.acceptRequest(requestId, user.getId());
        return ResponseEntity.ok(new ApiResponse("User request accepted"));
    }


    @PutMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(
            @AuthenticationPrincipal User user,
            @PathVariable Integer requestId
    ) {
        userRequestService.rejectRequest(requestId, user.getId());
        return ResponseEntity.ok(new ApiResponse("User request rejected"));
    }

}
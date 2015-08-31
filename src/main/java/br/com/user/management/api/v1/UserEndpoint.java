package br.com.user.management.api.v1;

import br.com.user.management.api.v1.resources.AddressResource;
import br.com.user.management.api.v1.resources.UserResource;
import br.com.user.management.chains.ChainLocator;
import br.com.user.management.chains.v1.UserContext;
import br.com.user.management.interceptors.Log;
import org.apache.commons.chain.impl.ChainBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.net.URI;

import static br.com.user.management.util.Envelop.newEnvelop;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.ok;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Path("/v1/users")
@ApplicationScoped
@Log
@Produces("application/json")
public class UserEndpoint {
    @Inject
    private ChainLocator chainLocator;

    @GET
    @Path("/{id}")
    public Response findUser(@PathParam("id") @NotNull(message = "id cannot be null") String id) throws Exception {
        final ChainBase fetchUserByIdChain = chainLocator.chain("fetchUserByIdChain");
        UserContext userContext = new UserContext(id);
        fetchUserByIdChain.execute(userContext);
        return ok(newEnvelop().item(userContext.getUserResource()).build()).build();
    }

    @GET
    @Path("/{id}/address")
    public Response findUserAddress(@PathParam("id") @NotNull(message = "id cannot be null") String id) throws Exception {
        final ChainBase fetchUserAddressByIdChain = chainLocator.chain("fetchUserAddressByIdChain");
        UserContext userContext = new UserContext(id);
        fetchUserAddressByIdChain.execute(userContext);
        return ok(newEnvelop().item(userContext.getAddressResource()).build()).build();
    }

    @POST
    @Consumes("application/json")
    public Response createUser(@NotNull(message = "body cannot be null") @Valid UserResource userResource) throws Exception {
        final ChainBase createUserChain = chainLocator.chain("createUserChain");
        UserContext userContext = new UserContext(userResource);
        createUserChain.execute(userContext);
        return created(new URI("/user-management/api/v1/users/" + userContext.getUser().getId())).entity(newEnvelop().item(userContext.getUserResource()).build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response updateUser(@PathParam("id") @NotNull(message = "id cannot be null") String id, @NotNull(message = "body cannot be null") UserResource userResource) throws Exception {
        final ChainBase updateUserChain = chainLocator.chain("updateUserChain");
        UserContext userContext = new UserContext(id, userResource);
        updateUserChain.execute(userContext);
        return ok(newEnvelop().item(userContext.getUserResource()).build()).build();
    }

    @POST
    @Path("/{id}/address")
    @Consumes("application/json")
    public Response addAddressToUserChain(@PathParam("id") @NotNull(message = "id cannot be null") String id, @NotNull @Valid AddressResource addressResource) throws Exception {
        final ChainBase addAddressToUserChain = chainLocator.chain("addAddressToUserChain");
        UserContext userContext = new UserContext(id, addressResource);
        addAddressToUserChain.execute(userContext);
        return created(new URI("/user-management/api/v1/users/" + userContext.getUser().getId()+"/address")).entity(newEnvelop().item(userContext.getAddressResource()).build()).build();
    }

    @PUT
    @Path("/{id}/address")
    @Consumes("application/json")
    public Response updateUserAddress(@PathParam("id") @NotNull(message = "id cannot be null") String id, @NotNull @Valid AddressResource addressResource) throws Exception {
        final ChainBase updateUserAddressChain = chainLocator.chain("updateUserAddressChain");
        UserContext userContext = new UserContext(id, addressResource);
        updateUserAddressChain.execute(userContext);
        return ok(newEnvelop().item(userContext.getAddressResource()).build()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") @NotNull(message = "id cannot be null") String id) throws Exception {
        final ChainBase deleteUserChain = chainLocator.chain("deleteUserChain");
        UserContext userContext = new UserContext(id);
        deleteUserChain.execute(userContext);
        return ok().build();
    }

    @DELETE
    @Path("/{id}/address")
    public Response deleteUserAddress(@PathParam("id") @NotNull(message = "id cannot be null") String id) throws Exception {
        final ChainBase deleteUserAddressChain = chainLocator.chain("deleteUserAddressChain");
        UserContext userContext = new UserContext(id);
        deleteUserAddressChain.execute(userContext);
        return ok().build();
    }
}
